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
import com.college_management.utils.CollegeUtils;
import com.college_management.utils.StudentPayload;
import com.college_management.utils.StudentResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public StudentResponse addStudent(StudentPayload student) {
		log.info("Adding student: {}", student);
		if (repository.findById(student.getId()).isEmpty()) {

			student.setUserType("student");

			int totalMarks = student.getTotalMarks();

			if (totalMarks > 450) {
				student.setGrade('A');
			} else if (totalMarks > 350 && totalMarks < 450) {
				student.setGrade('B');
			} else {
				student.setGrade('C');
			}

			repository.save(CollegeUtils.userConverter(student));
			return StudentResponse.builder().student(student).message("Student added successfully").build();
		}
		return StudentResponse.builder().message("User name already exists").build();

	}

	@Override
	public List<StudentResponse> addStudents(List<StudentPayload> students) {
		log.info("Adding multiple students: {}", students);
		List<StudentResponse> studentResponseList = new ArrayList<>();

		for (StudentPayload student : students) {
			if (repository.findById(student.getId()).isEmpty()) {

				student.setUserType("student");

				int totalMarks = student.getTotalMarks();

				if (totalMarks > 450) {
					student.setGrade('A');
				} else if (totalMarks > 350 && totalMarks < 450) {
					student.setGrade('B');
				} else {
					student.setGrade('C');
				}

				repository.save(CollegeUtils.userConverter(student));
				studentResponseList.add(StudentResponse.builder().student(student).message("Student added successfully").build());

			} else {
				studentResponseList.add(StudentResponse.builder().message("User name already exists").build());
			}
		}
		return studentResponseList;
	}

	@Override
	public ResponseEntity<List<StudentResponse>> fetchStudents(String identifierType, String identifierValue) {
		log.info("Fetching students");
		List<StudentResponse> resultList = new ArrayList<>();

		Query query = new Query();
		query.addCriteria(Criteria.where(identifierType).is(identifierValue));
		List<User> foundList = mongoTemplate.find(query, User.class);

		for (User user : foundList) {
			if (user.getUserType().equals("student")) {
				StudentResponse userToStudentConverter = CollegeUtils.userToStudentConverter(user);
				userToStudentConverter.setMessage("Student fetched successfully");
				resultList.add(userToStudentConverter);
			}
		}

		if (resultList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}

	@Override
	public List<StudentResponse> fetchAllStudents() {
		log.info("Fetching all the students");
		List<StudentResponse> resultStudents = new ArrayList<>();
		List<User> foundUsers = repository.findAll();

		for (User student : foundUsers) {
			if (student.getUserType().equals("student")) {
				StudentResponse userToStudentConverter = CollegeUtils.userToStudentConverter(student);
				userToStudentConverter.setMessage("Student fetched successfully");
				resultStudents.add(userToStudentConverter);
			}
		}
		return resultStudents;
	}

}
