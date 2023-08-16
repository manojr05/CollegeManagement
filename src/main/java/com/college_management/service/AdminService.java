package com.college_management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.college_management.model.User;
import com.college_management.utils.UserResponse;

public interface AdminService {

	UserResponse addStudent(User student);
	UserResponse addTeacher(User user);
	List<UserResponse> addStudents(List<User> students);
	List<UserResponse> addTeachers(List<User> teachers);
	UserResponse deleteStudent(String id);
	UserResponse deleteTeacher(String id);
	UserResponse modifyStudent(User student);
	UserResponse modifyTeacher(User teacher);
	ResponseEntity<List<UserResponse>> fetchStudents(String identifierType, String identifierValue);
	ResponseEntity<List<UserResponse>> fetchTeachers(String identifierType, String identifierValue);
	List<UserResponse> fetchAllStudents();
	List<UserResponse> fetchAllTeachers();

}
