package com.college_management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college_management.model.User;
import com.college_management.utils.UserResponse;

@Service
public interface TeacherService {

	UserResponse addStudent(User student);
	List<UserResponse> addStudents(List<User> students);
	ResponseEntity<List<UserResponse>> fetchStudents(String identifierType, String identifierValue);
	List<UserResponse> fetchAllStudents();

}
