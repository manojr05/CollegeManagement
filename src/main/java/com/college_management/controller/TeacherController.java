package com.college_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college_management.service.TeacherService;
import com.college_management.utils.StudentPayload;
import com.college_management.utils.StudentResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

	@Autowired
	private TeacherService service;

	@PostMapping("/addStudent")
	public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentPayload user) {
		log.info("Adding student: {}", user);
		StudentResponse studentResponse = service.addStudent(user);
		if (studentResponse.getStudent()!=null) {
			log.info("Student addition response: {}", studentResponse);
			return new ResponseEntity<>(studentResponse, HttpStatus.OK);
		}
		log.error("Adding student failed: {}", studentResponse.getMessage());
		return new ResponseEntity<>(studentResponse, HttpStatus.CONFLICT);
	}

	@PostMapping("/addStudents")
	public ResponseEntity<List<StudentResponse>> addStudents(@RequestBody List<StudentPayload> students) {
		log.info("Adding students: {}", students);
		boolean flag = true;
		List<StudentResponse> addedStudents = service.addStudents(students);
		for (StudentResponse student : addedStudents) {
			if (student.getStudent()==null) {
				flag = false;
				break;
			}
		}
		if (flag) {
			log.info("Added students response: {}", addedStudents);
			return new ResponseEntity<>(addedStudents, HttpStatus.OK);
		}
		log.error("Added students response with conflicts: {}", addedStudents);
		return new ResponseEntity<>(addedStudents, HttpStatus.CONFLICT);
	}

	@GetMapping("/fetchStudents")
    public ResponseEntity<List<StudentResponse>> fetchStudents(@RequestParam String identifierType, @RequestParam String identifierValue) {
        log.info("Received request to fetch students with identifier type: {} and value: {}", identifierType, identifierValue);
        ResponseEntity<List<StudentResponse>> response = service.fetchStudents(identifierType, identifierValue);
        log.info("Returning response for fetching students: {}", response.getBody());
        return response;
    }


	@GetMapping("/fetchAllStudents")
    public ResponseEntity<List<StudentResponse>> fetchAllStudents() {
        log.info("Received request to fetch all students");
        ResponseEntity<List<StudentResponse>> response = new ResponseEntity<>(service.fetchAllStudents(), HttpStatus.OK);
        log.info("Returning response for fetching all students: {}", response.getBody());
        return response;
    }
}
