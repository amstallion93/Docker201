package com.yorbit.docker.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yorbit.docker.entity.User;
import com.yorbit.docker.exception.UsernameNotFoundException;
import com.yorbit.docker.repo.UserRepository;
@Service
public class UserDaoImpl {

	@Autowired
	private UserRepository userRepository;


	public User createNewUser(User user) throws Exception {
		return userRepository.save(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findbyId(String userName) throws Exception {
		User user = userRepository.getOne(userName);
		return user;

	}

	public User editUserProfile(String userName, User userDetails) {
		User user = userRepository.getOne(userName);

		if (userDetails.getNumber().isEmpty() || userDetails.getNumber() == null) {
			user.setNumber(user.getNumber());
		} else {
			user.setNumber(userDetails.getNumber());
		}

		if (userDetails.getAddress().isEmpty() || userDetails.getAddress() == null) {
			user.setAddress(user.getAddress());
		} else {
			user.setAddress(userDetails.getAddress());
		}

		if (userDetails.getName().isEmpty() || userDetails.getName() == null) {
			user.setName(user.getName());
		} else {
			user.setName(userDetails.getName());
		}

		if (userDetails.getPassword().isEmpty() || userDetails.getPassword() == null) {
			user.setName(user.getName());
		}

		user.setUserName(user.getUserName());
		User use = userRepository.save(user);
		return use;

	}

	public void deleteUser(String userName) {
		userRepository.deleteById(userName);
	}

	public boolean deactivateUser(String userName) throws UsernameNotFoundException {
		User user = userRepository.getOne(userName);
		user.setActive(false);
		userRepository.save(user);
		return false;
	}

}
