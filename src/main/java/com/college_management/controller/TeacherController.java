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

import com.college_management.model.User;
import com.college_management.service.TeacherService;
import com.college_management.utils.UserResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

	@Autowired
	private TeacherService service;

	@PostMapping("/addStudent")
	public ResponseEntity<UserResponse> addStudent(@RequestBody User user) {
		log.info("Received request to add student: {}", user);
		UserResponse studentResponse = service.addStudent(user);
		if (studentResponse.getUser()!=null) {
			log.info("Student addition response: {}", studentResponse);
			return new ResponseEntity<>(studentResponse, HttpStatus.OK);
		}
		log.error("Adding student failed: {}", studentResponse.getMessage());
		return new ResponseEntity<>(studentResponse, HttpStatus.CONFLICT);
	}
	
	@PostMapping("/addStudents")
	public ResponseEntity<List<UserResponse>> addStudents(@RequestBody List<User> students) {
		log.info("Received request to add students: {}", students);
		boolean flag = true;
		List<UserResponse> addedStudents = service.addStudents(students);
		for (UserResponse student : addedStudents) {
			if (student.getUser()==null) {
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
    public ResponseEntity<List<UserResponse>> fetchStudents(@RequestParam String identifierType, @RequestParam String identifierValue) {
        log.info("Received request to fetch students with identifier type: {} and value: {}", identifierType, identifierValue);
        ResponseEntity<List<UserResponse>> response = service.fetchStudents(identifierType, identifierValue);
        if(!response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {        	
        	log.info("Returning response for fetching students: {}", response.getBody());
        	return response;
        }
        log.info("No students found");
        return response;
    }
	
	@GetMapping("/fetchAllStudents")
    public ResponseEntity<List<UserResponse>> fetchAllStudents() {
        log.info("Received request to fetch all students");
        List<UserResponse> fetchAllStudents = service.fetchAllStudents();
        if(fetchAllStudents!=null) {        	
        	ResponseEntity<List<UserResponse>> response = new ResponseEntity<>(fetchAllStudents, HttpStatus.OK);
        	log.info("Returning response for fetching all students: {}", response.getBody());
        	return response;
        }
        log.error("No students found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
