package com.yorbit.docker.entity;

public class UserResponse extends Response {


	public UserResponse(int statusCode, String status) {
		super(statusCode, status);
	}

	public UserResponse() {
		// TODO Auto-generated constructor stub
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
