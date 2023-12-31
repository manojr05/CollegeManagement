package com.college_management.service_impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college_management.model.User;
import com.college_management.repository.UserRepository;
import com.college_management.service.TeacherService;
import com.college_management.utils.UserResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserResponse addStudent(User student) {
		log.info("Adding student: {}", student);
		if (repository.findById(student.getId()).isEmpty()) {

			student.setUserType("student");

			int totalMarks = student.getTotalMarks();

			if (totalMarks >= 450) {
				student.setGrade('A');
			} else if (totalMarks >= 350 && totalMarks <= 450) {
				student.setGrade('B');
			} else {
				student.setGrade('C');
			}

			repository.save(student);
			return UserResponse.builder().user(student).message("Student added successfully").build();
		}
		return UserResponse.builder().message("User name already exists").build();

	}

	@Override
	public List<UserResponse> addStudents(List<User> students) {
		log.info("Adding multiple students: {}", students);
		List<UserResponse> studentResponseList = new ArrayList<>();

		for (User student : students) {
			if (repository.findById(student.getId()).isEmpty()) {

				student.setUserType("student");

				int totalMarks = student.getTotalMarks();

				if (totalMarks >= 450) {
					student.setGrade('A');
				} else if (totalMarks >= 350 && totalMarks <= 450) {
					student.setGrade('B');
				} else {
					student.setGrade('C');
				}

				repository.save(student);
				studentResponseList.add(UserResponse.builder().user(student).message("Student added successfully").build());

			} else {
				studentResponseList.add(UserResponse.builder().message("User name already exists").build());
			}
		}
		return studentResponseList;
	}

	@Override
	public ResponseEntity<List<UserResponse>> fetchStudents(String identifierType, String identifierValue) {
		log.info("Fetching students");
		List<UserResponse> resultList = new ArrayList<>();
		Query query = new Query();
		query.addCriteria(Criteria.where(identifierType).is(identifierValue));
		List<User> foundList = mongoTemplate.find(query, User.class);
		
		for (User user : foundList) {
			if (user.getUserType().equals("student")) {
				resultList.add(UserResponse.builder().user(user).message("Student fetched successfully").build());
			}
		}

		if (resultList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}

	@Override
	public List<UserResponse> fetchAllStudents() {
		log.info("Fetching all the students");
		List<UserResponse> resultStudents = new ArrayList<>();
		List<User> foundUsers = repository.findByUserType("student");

		if(foundUsers.size()!=0) {			
			for (User student : foundUsers) {
				resultStudents.add(UserResponse.builder().user(student).message("Student fetched successfully").build());
			}
		}
		if(resultStudents.size()!=0) {			
			return resultStudents;
		}
		return null;
	}
}
