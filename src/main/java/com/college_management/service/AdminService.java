package com.college_management.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.college_management.utils.StudentPayload;
import com.college_management.utils.StudentResponse;
import com.college_management.utils.TeacherPayload;
import com.college_management.utils.TeacherResponse;

public interface AdminService {

	StudentResponse addStudent(StudentPayload student);
	TeacherResponse addTeacher(TeacherPayload user);
	List<StudentResponse> addStudents(List<StudentPayload> students);
	List<TeacherResponse> addTeachers(List<TeacherPayload> teachers);
	StudentResponse deleteStudent(String id);
	TeacherResponse deleteTeacher(String id);
	StudentResponse modifyStudent(StudentPayload student);
	TeacherResponse modifyTeacher(TeacherPayload teacher);
	ResponseEntity<List<StudentResponse>> fetchStudents(String identifierType, String identifierValue);
	ResponseEntity<List<TeacherResponse>> fetchTeachers(String identifierType, String identifierValue);
	List<StudentResponse> fetchAllStudents();
	List<TeacherResponse> fetchAllTeachers();

}
