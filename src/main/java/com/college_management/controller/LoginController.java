package com.college_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college_management.service.LoginService;
import com.college_management.utils.AdminResponse;
import com.college_management.utils.StudentResponse;
import com.college_management.utils.TeacherResponse;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {

	@Autowired
	private LoginService service;

	@PostMapping("/login")
    public ResponseEntity<AdminResponse> loginAdmin(@RequestParam String userName, @RequestParam String password) {
        log.info("Admin login request received for username: {}", userName);
        AdminResponse loginAdmin = service.loginAdmin(userName, password);
        log.info("Admin login response: {}", loginAdmin);
        if (loginAdmin.getMessage().contains("Successfull")) {
            log.info("Admin login successful for username: {}", userName);
            return new ResponseEntity<>(loginAdmin, HttpStatus.OK);
        } else if (loginAdmin.getMessage().contains("Incorrect")) {
            log.error("Admin login failed for username: {} Message: {}", userName, loginAdmin.getMessage());
            return new ResponseEntity<>(loginAdmin, HttpStatus.FORBIDDEN);
        }
        log.error("Admin not found for username: {}", userName);
        return new ResponseEntity<>(loginAdmin, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/student/login")
    public ResponseEntity<StudentResponse> loginStudent(@RequestParam String userName, @RequestParam String password, HttpSession session) {
        log.info("Student login request received for username: {}", userName);
        StudentResponse loginStudent = service.loginStudent(userName, password, session);
        if (loginStudent.getMessage().contains("Successfull")) {
            log.info("Student login successful for username: {}", userName);
            log.info("Student login response: {}", loginStudent);
            return new ResponseEntity<>(loginStudent, HttpStatus.OK);
        } else if (loginStudent.getMessage().contains("Incorrect")) {
        	log.error("Student login failed for username: {} Message: {}", userName, loginStudent.getMessage());
            return new ResponseEntity<>(loginStudent, HttpStatus.FORBIDDEN);
        }
        log.error("Student not found for username: {}", userName);
        return new ResponseEntity<>(loginStudent, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/teacher/login")
    public ResponseEntity<TeacherResponse> loginTeacher(@RequestParam String userName, @RequestParam String password) {
        log.info("Teacher login request received for username: {}", userName);
        TeacherResponse loginTeacher = this.service.loginTeacher(userName, password);
        if (loginTeacher.getMessage().contains("Successfull")) {
            log.info("Teacher login successful for username: {}", userName);
            log.info("Teacher login response: {}", loginTeacher);
            return new ResponseEntity<>(loginTeacher, HttpStatus.OK);
        } else if (loginTeacher.getMessage().contains("Incorrect")) {
        	log.error("Teacher login failed for username: {} Message: {}", userName, loginTeacher.getMessage());
            return new ResponseEntity<>(loginTeacher, HttpStatus.FORBIDDEN);
        }
        log.error("Teacher not found for username: {}", userName);
        return new ResponseEntity<>(loginTeacher, HttpStatus.NOT_FOUND);
    }



}
