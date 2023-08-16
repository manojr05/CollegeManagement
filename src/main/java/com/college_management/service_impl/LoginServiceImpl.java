package com.college_management.service_impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college_management.model.User;
import com.college_management.repository.UserRepository;
import com.college_management.service.LoginService;
import com.college_management.utils.UserResponse;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserResponse loginAdmin(String userName, String password) {
		log.info("Logging in as admin");
		Optional<User> user=repository.findById(userName);
		if(user.isPresent() && user.get().getUserType().equals("admin")) {

			if(user.get().getPassword().equals(password)) {
				return UserResponse.builder().message("Admin Login Successfull").user(user.get()).build();
			}else {
				return UserResponse.builder().message("Incorrect Password").build();
			}
		}
		return UserResponse.builder().message("No admin found").build();
		
	}

	@Override
	public UserResponse loginStudent(String userName, String password, HttpSession session) {
		log.info("Logging in as student");
		Optional<User> user=repository.findById(userName);
		if(user.isPresent() && user.get().getUserType().equals("student")) {

			if(user.get().getPassword().equals(password)) {
				session.setAttribute("student", user.get());
				return UserResponse.builder().message("Student Login Successfull").user(user.get()).build();
			}else {
				return UserResponse.builder().message("Incorrect Password").build();
			}
		}else {
			return UserResponse.builder().message("No student found").build();
		}
	}

	@Override
	public UserResponse loginTeacher(String userName, String password) {
		log.info("Logging in as teacher");
		Optional<User> user=repository.findById(userName);
		if(user.isPresent() && user.get().getUserType().equals("teacher")) {

			if(user.get().getPassword().equals(password)) {
				return UserResponse.builder().message("Teacher Login Successfull").user(user.get()).build();
			}else {
				return UserResponse.builder().message("Incorrect Password").build();
			}
		}else {
			return UserResponse.builder().message("No teacher found").build();
		}
	}
}
