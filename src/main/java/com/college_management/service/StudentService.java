package com.college_management.service;

import org.springframework.stereotype.Service;
import com.college_management.utils.UserResponse;

import jakarta.servlet.http.HttpSession;

@Service
public interface StudentService {

	UserResponse fetchMyDetails(HttpSession session);
	String logoutStudent(HttpSession session);

}
