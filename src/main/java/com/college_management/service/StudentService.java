package com.college_management.service;

import org.springframework.stereotype.Service;

import com.college_management.utils.StudentResponse;

import jakarta.servlet.http.HttpSession;

@Service
public interface StudentService {

	StudentResponse fetchMyDetails(HttpSession session);
	String logoutStudent(HttpSession session);

}
