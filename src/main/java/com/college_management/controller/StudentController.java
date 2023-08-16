package com.college_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college_management.service.StudentService;
import com.college_management.utils.UserResponse;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

	@Autowired
	private StudentService service;

	@GetMapping("/getMyDetails")
	public ResponseEntity<UserResponse> fetchMyDetails(HttpSession session){
		UserResponse myDetails = service.fetchMyDetails(session);
		log.info("Got request to fetch the student details.");
		if(myDetails.getUser()!=null) {
			log.info("Details fetched response: {}", myDetails.getUser());
			return new ResponseEntity<>(myDetails, HttpStatus.OK);
		}
		log.error("Failed to fetch. Error message: {}", myDetails.getMessage());
		return new ResponseEntity<>(myDetails, HttpStatus.REQUEST_TIMEOUT);
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logoutStudent(HttpSession session){
		log.info("Got request for student logout");
		String message = service.logoutStudent(session);

		if(message.contains("success"))
			log.info(message);
		else
			log.error(message);

		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
