package com.college_management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college_management.utils.StudentPayload;
import com.college_management.utils.StudentResponse;

@Service
public interface TeacherService {

	StudentResponse addStudent(StudentPayload student);
	List<StudentResponse> addStudents(List<StudentPayload> students);
	ResponseEntity<List<StudentResponse>> fetchStudents(String identifierType, String identifierValue);
	List<StudentResponse> fetchAllStudents();

}
