package com.yorbit.docker.entity;

import java.util.List;


public class UserListResponse extends Response {

	public UserListResponse(int statusCode, String status) {
		super(statusCode, status);
	}

	public UserListResponse() {
		// TODO Auto-generated constructor stub
	}

	private List<User> userList;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
