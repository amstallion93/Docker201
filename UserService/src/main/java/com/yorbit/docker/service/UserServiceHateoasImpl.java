package com.yorbit.docker.service;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yorbit.docker.entity.Response;
import com.yorbit.docker.entity.User;
import com.yorbit.docker.entity.UserListResponse;
import com.yorbit.docker.entity.UserResponse;

@Service

public class UserServiceHateoasImpl {

	private final String endpoint = "http://localhost:9001/";

	public Resource<UserListResponse> getAllUsers(UserListResponse userlistResponse) {

		Resource<UserListResponse> resource = new Resource<UserListResponse>(userlistResponse);

		if (!CollectionUtils.isEmpty(userlistResponse.getUserList())) {

			for (User link : userlistResponse.getUserList()) {

				Link selflink = new Link(endpoint + "userservice/user/getUserById/" + link.getUserName());
				resource.add(selflink);

			}
		}

		return resource;
	}

	public Resource<Response> getById(Response response, String userName) {

		Resource<Response> resource = new Resource<Response>(response);

		if (response.getStatusCode() != 204) {

			Link selflink = new Link(endpoint + "userservice/user/deactivateUser/" + userName);
			resource.add(selflink);
			Link selflink1 = new Link(endpoint + "userservice/user/editProfile/" + userName);
			resource.add(selflink1);

		}

		return resource;
	}

	public Resource<UserResponse> editProfile(UserResponse userResponse, String userName) {

		Resource<UserResponse> resource = new Resource<UserResponse>(userResponse);

		if (userResponse.getStatusCode() != 204) {

			Link selflink = new Link(endpoint + "userservice/user/deactivateUser/" + userName);
			resource.add(selflink);

		}
		return resource;
	}

}
