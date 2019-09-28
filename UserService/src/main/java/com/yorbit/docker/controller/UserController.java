package com.yorbit.docker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yorbit.docker.entity.Response;
import com.yorbit.docker.entity.User;
import com.yorbit.docker.entity.UserListResponse;
import com.yorbit.docker.entity.UserResponse;
import com.yorbit.docker.service.UserServiceHateoasImpl;
import com.yorbit.docker.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private UserServiceHateoasImpl userServiceHateOas;

	@PostMapping("/newUser")
	public Response newUser(@Valid @RequestBody User user) {
		return userService.createNewUser(user);
	}

	@GetMapping("/getAllUsers")
	public Resource<UserListResponse> getAllusers() {
		return userServiceHateOas.getAllUsers(userService.findAllUsers());

	}

	@GetMapping("/getUserById/{userName}")
	public Resource<Response> getUserById(@PathVariable(value = "userName") String userName) {
		Response response = userService.getUserById(userName);
		return userServiceHateOas.getById(response, userName);

	}

	@PutMapping("/editProfile/{userName}")
	public Resource<UserResponse> editUserProfile(@PathVariable(value = "userName") String userName,
			@Valid @RequestBody User userDetails) {
		UserResponse userResponse = userService.editUserProfile(userName, userDetails);
		Resource<UserResponse> resource = userServiceHateOas.editProfile(userResponse, userName);
		return resource;

	}


	@GetMapping("/getById/{userName}")
	public ResponseEntity<User> getUserByIdAdmin(@PathVariable(value = "userName") String userName) {
		User user = userService.getUserByIdAdmin(userName);
		return ResponseEntity.ok().body(user);

	}

	@PutMapping("/deactivateUser/{userName}")
	public Response deactivateUser(@PathVariable(value = "userName") String userName) {
		return userService.deactiveUser(userName);

	}

	@GetMapping("/getUserAddress/{userName}")
	public String getUserAddress(@PathVariable(value = "userName") String userName) {
		String userAddress = userService.getUserAddress(userName);
		return userAddress;
	}
	
	@PostMapping("/newSocial")
	public Response newSocail(@Valid @RequestBody User user) {
		return userService.createNewSocailUser(user);

	}
	

}
