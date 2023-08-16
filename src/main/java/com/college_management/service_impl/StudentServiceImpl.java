package com.college_management.service_impl;

import org.springframework.stereotype.Service;

import com.college_management.service.StudentService;
import com.college_management.utils.UserResponse;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

	@Override
	public UserResponse fetchMyDetails(HttpSession session) {
		log.info("Trying to fetch student details");
		UserResponse student = (UserResponse)session.getAttribute("student");
		if(student!=null) {
			student.setMessage("Fetched Successfully");
			return student;
		}

		return UserResponse.builder().message("Please login as student and try again").build();
	}

	@Override
	public String logoutStudent(HttpSession session) {
		log.info("trying to logout");
		UserResponse student = (UserResponse)session.getAttribute("student");
		if(student!=null) {
			session.removeAttribute("student");
			return "student logged out successfully";
		}
		return "please login first and then logout";
	}

}
