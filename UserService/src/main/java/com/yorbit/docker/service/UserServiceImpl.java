package com.yorbit.docker.service;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yorbit.docker.dao.UserDaoImpl;
import com.yorbit.docker.entity.Response;
import com.yorbit.docker.entity.User;
import com.yorbit.docker.entity.UserListResponse;
import com.yorbit.docker.entity.UserResponse;
import com.yorbit.docker.exception.UserException;
import com.yorbit.docker.exception.UsernameNotFoundException;



@Service
@PropertySource("classpath:application.properties")
public class UserServiceImpl  {

	@Autowired
	private UserDaoImpl userDaoImpl;

	private final String notFoundMessage = "Not found";

	private final int notFoundStatus = 404;

	private final String inValidMessage = "Invalid";

	private final int inValidCode = 500;

	private final Log LOGGER = LogFactory.getLog(this.getClass());

	public Response createNewUser(User user) {

		Response response = new Response();

		String emailRegex = "^[a-zA-Z0-9]+(?:\\." + "[a-zA-Z0-9]+)*@" + "(?:[a-zA-Z0-9]+\\.)+[a-z" + "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);

		try {
			if (userDaoImpl.findbyId(user.getUserName()).isActive()) {
				throw new UserException("user already exists");
			}
		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new Response(204, "User with username already exsits");
		} catch (Exception e) {
			System.out.println(e);
			response.setStatus("Something went wrong , please try again");
			response.setStatusCode(204);
		}
		if (pat.matcher(user.getUserName()).matches()) {
			User newUser;
			try {
				newUser = userDaoImpl.createNewUser(user);
				if (newUser != null) {
					response.setStatusCode(200);
					response.setStatus("User successfully registered");

				} else {
					response.setStatusCode(204);
					response.setStatus("Somthing went wrong , please try again");
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.setStatusCode(204);
				response.setStatus("Somthing went wrong , please try again");
			}

		} else {
			response.setStatusCode(204);
			response.setStatus("The username is invalid , Please provide valid username");
		}

		return response;
	}

	public UserListResponse findAllUsers() {

		UserListResponse userListResponse = null;
		try {
			List<User> userList = userDaoImpl.findAllUsers();
			userListResponse = new UserListResponse();

			if (!CollectionUtils.isEmpty(userList)) {
				userList.forEach(list -> {
					list.setPassword("***********");
				});

				userListResponse.setStatusCode(200);
				userListResponse.setStatus("All users list");
				userListResponse.setUserList(userList);

			} else {

				userListResponse.setStatusCode(notFoundStatus);
				userListResponse.setStatus(notFoundMessage);

			}
		} catch (Exception e) {
			userListResponse.setStatusCode(notFoundStatus);
			userListResponse.setStatus("Somthing went wrong , please try again");
		}

		return userListResponse;
	}

	public UserResponse getUserById(String userName) {
		User user = new User();
		UserResponse userResp = new UserResponse();

		try {
			user = userDaoImpl.findbyId(userName);
			userResp = new UserResponse();

			if (user != null && user.isActive() == true) {

				user.setPassword("***************");
				userResp.setStatusCode(200);
				userResp.setStatus("User found with the specified username");
				userResp.setUser(user);

			} else {

				throw new UsernameNotFoundException("User not found with the specified username");

			}
		} catch (UsernameNotFoundException usernameNotFound) {
			userResp.setStatus(notFoundMessage);
			userResp.setStatusCode(inValidCode);
		} catch (Exception e) {
			userResp.setStatus(notFoundMessage);
			userResp.setStatusCode(inValidCode);
		}

		return userResp;
	}

	public UserResponse editUserProfile(String userName, User userDetails) {
		User user = new User();
		UserResponse userResponse = new UserResponse();

		try {
			user = userDaoImpl.editUserProfile(userName, userDetails);
			userResponse = new UserResponse();

			if (user.getUserName().equals(userName) && user.isActive() != false) {

				if ((userDetails.getUserName().equals(userName))) {
					user.setPassword("***************");
					userResponse.setStatusCode(200);
					userResponse.setStatus("User information edited successfully");
					userResponse.setUser(user);
				} else {
					userResponse.setStatusCode(204);
					userResponse.setStatus("User is restricted to edit username");
				}

			} else {

				userResponse.setStatusCode(notFoundStatus);
				userResponse.setStatus(notFoundMessage);
			}
		} catch (Exception e) {
			userResponse.setStatus(notFoundMessage);
			userResponse.setStatusCode(inValidCode);
			return userResponse;
		}

		return userResponse;

	}

	public User getUserByIdAdmin(String userName) {

		User user = new User();
		try {
			 User user1 = userDaoImpl.findbyId(userName);
			if(user1.isActive()==true)
				return user1;
		} catch (Exception e) {
			LOGGER.debug("The user name specified is invalid");

		}
		return user;
	}

	
	public Response deactiveUser(String userName) {

		User user = new User();
		Response response = new Response();
		try {

			user = userDaoImpl.findbyId(userName);
			response = new Response();

			if (user.getUserName().equals(userName) && user.isActive() != false && user != null) {

				user.setActive(userDaoImpl.deactivateUser(userName));
				response.setStatusCode(200);
				response.setStatus("User deactivated successfully");

			} else {

				response.setStatusCode(notFoundStatus);
				response.setStatus(notFoundMessage);

			}
		} catch (NullPointerException e) {
			response.setStatusCode(notFoundStatus);
			response.setStatus(notFoundMessage);
			return response;
		} catch (Exception e) {
			response.setStatus(notFoundMessage);
			response.setStatusCode(inValidCode);
			return response;
		}

		return response;
	}


	public String getUserAddress(String userName) {
		User user = null;
		try {
			user = userDaoImpl.findbyId(userName);
			return user.getAddress();
		} catch (Exception e) {
			LOGGER.debug("The user name specified is invalid");
			return "null";

		}

	}

	public Response createNewSocailUser(User user) {

		Response response = new Response();

		String emailRegex = "^[a-zA-Z0-9]+(?:\\." + "[a-zA-Z0-9]+)*@" + "(?:[a-zA-Z0-9]+\\.)+[a-z" + "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);

		try {
			if (userDaoImpl.findbyId(user.getUserName()).isActive()) {
				throw new UserException("user already exists");
			}
		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new Response(204, "User with username already exsits");
		} catch (Exception e) {
			System.out.println(e);
			response.setStatus("Something went wrong , please try again");
			response.setStatusCode(204);
		}
		if (pat.matcher(user.getUserName()).matches()) {
			User newUser;
			try {
				newUser = userDaoImpl.createNewUser(user);
				if (newUser != null) {
					response.setStatusCode(200);
					response.setStatus("User successfully registered");

				} else {
					response.setStatusCode(204);
					response.setStatus("Somthing went wrong , please try again");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			response.setStatusCode(204);
			response.setStatus("The username is invalid , Please provide valid username");
		}

		return response;
	}

}
