package com.college_management.service;

import org.springframework.stereotype.Service;
import com.college_management.utils.UserResponse;
import jakarta.servlet.http.HttpSession;

@Service
public interface LoginService {

	UserResponse loginAdmin(String userName, String password);
	UserResponse loginStudent(String userName, String password, HttpSession session);
	UserResponse loginTeacher(String userName, String password);

}
