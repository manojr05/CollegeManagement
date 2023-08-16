
package com.college_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college_management.model.User;
import com.college_management.service.AdminService;
import com.college_management.utils.UserResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	private AdminService service;

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


	@PostMapping("/addTeacher")
	public ResponseEntity<UserResponse> addTeacher(@RequestBody User user) {
		log.info("Received request to add teacher: {}", user);
		UserResponse teacherResponse = service.addTeacher(user);
		if (teacherResponse.getUser()!=null) {
			log.info("Teacher addition response: {}", teacherResponse);
			return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
		}
		log.error("Adding teacher failed: {}", teacherResponse.getMessage());
		return new ResponseEntity<>(teacherResponse, HttpStatus.CONFLICT);
	}


	@PostMapping("/addTeachers")
	public ResponseEntity<List<UserResponse>> addTeachers(@RequestBody List<User> teachers) {
		log.info("Received request to add teachers: {}", teachers);
		boolean flag = true;
		List<UserResponse> addedTeachers = service.addTeachers(teachers);
		for (UserResponse teacher : addedTeachers) {
			if (teacher.getMessage().contains("already")) {
				flag = false;
				break;
			}
		}
		if (flag) {
			log.info("Added teachers response: {}", teachers);
			return new ResponseEntity<>(addedTeachers, HttpStatus.OK);
		}
		log.error("Added teachers response with conflicts: {}", addedTeachers);
		return new ResponseEntity<>(addedTeachers, HttpStatus.CONFLICT);
	}

	@PutMapping("/modifyStudent")
	public ResponseEntity<UserResponse> modifyStudent(@RequestBody User student) {
		log.info("Received request to modify student. Student ID: {}", student.getId());
		UserResponse userResponse = service.modifyStudent(student);
		if (userResponse.getUser()!=null) {
			log.info("Student modification successful. Student ID: {}", student.getId());
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}
		log.error("Student data not found. Student ID: {}", student.getId());
		return new ResponseEntity<>(userResponse, HttpStatus.NOT_FOUND);
	}

	@PutMapping("/modifyTeacher")
	public ResponseEntity<UserResponse> modifyTeacher(@RequestBody User teacher) {
		log.info("Received request to modify teacher. Teacher ID: {}", teacher.getId());
		UserResponse userResponse = service.modifyTeacher(teacher);
		if (userResponse.getUser()!=null) {
			log.info("Teacher modification successful. Teacher ID: {}", teacher.getId());
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}
		log.error("Teacher data not found. Teacher ID: {}", teacher.getId());
		return new ResponseEntity<>(userResponse, HttpStatus.NOT_FOUND);
	}

	

    @GetMapping("/fetchTeachers")
    public ResponseEntity<List<UserResponse>> fetchTeachers(@RequestParam String identifierType, @RequestParam String identifierValue) {
        log.info("Received request to fetch teachers with identifier type: {} and value: {}", identifierType, identifierValue);
        ResponseEntity<List<UserResponse>> response = service.fetchTeachers(identifierType, identifierValue);
        if(!response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {        	
        	log.info("Returning response for fetching teachers: {}", response.getBody());
        	return response;
        }
        log.info("No teachers found");
        return response;
        
    }

    
    @GetMapping("/fetchAllTeachers")
    public ResponseEntity<List<UserResponse>> fetchAllTeachers() {
        log.info("Received request to fetch all teachers");
        List<UserResponse> fetchAllTeachers = service.fetchAllTeachers();
        if(fetchAllTeachers!=null) {        	
        	ResponseEntity<List<UserResponse>> response = new ResponseEntity<>(fetchAllTeachers, HttpStatus.OK);
        	log.info("Returning response for fetching all teachers: {}", response.getBody());
        	return response;
        }
        log.error("No teachers found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

	@DeleteMapping("/deleteStudent")
	public ResponseEntity<UserResponse> deleteStudent(@RequestParam String id) {
		log.info("Got request to delete student with UserName: {}", id);
		UserResponse deletedStudent = service.deleteStudent(id);
		if (deletedStudent.getUser()==null) {
			log.info("Not able to find student with ID: {}", id);
			return new ResponseEntity<>(deletedStudent, HttpStatus.NOT_FOUND);
		}
		log.info("Deleted student with ID: {}", id);
		return new ResponseEntity<>(deletedStudent, HttpStatus.OK);
	}

	@DeleteMapping("/deleteTeacher")
	public ResponseEntity<UserResponse> deleteTeacher(@RequestParam String id) {
		log.info("Got request to delete teacher with UserName: {}", id);
		UserResponse deletedStudent = service.deleteTeacher(id);
		if (deletedStudent.getUser()==null) {
			log.info("Not able to find teacher with ID: {}", id);
			return new ResponseEntity<>(deletedStudent, HttpStatus.NOT_FOUND);
		}
		log.info("Deleted teacher with ID: {}", id);
		return new ResponseEntity<>(deletedStudent, HttpStatus.OK);
	}

}
