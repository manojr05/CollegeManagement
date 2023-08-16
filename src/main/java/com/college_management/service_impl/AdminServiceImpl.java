package com.college_management.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college_management.model.User;
import com.college_management.repository.UserRepository;
import com.college_management.service.AdminService;
import com.college_management.utils.UserResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

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
	public UserResponse addTeacher(User teacher) {
		log.info("Adding teacher: {}", teacher);
		if (repository.findById(teacher.getId()).isEmpty()) {
			teacher.setUserType("teacher");
			repository.save(teacher);
			return UserResponse.builder().user(teacher).message("Teacher added successfully").build();
		}
		return UserResponse.builder().message("User name already exists").build();
	}

	@Override
	public List<UserResponse> addTeachers(List<User> teachers) {
		log.info("Adding multiple teachers: {}", teachers);
		List<UserResponse> userResponseList = new ArrayList<>();

		for (User teacher : teachers) {
			if (repository.findById(teacher.getId()).isEmpty()) {

				teacher.setUserType("teacher");
				repository.save(teacher);

				userResponseList
						.add(UserResponse.builder().user(teacher).message("Teacher added successfully").build());
			} else {
				userResponseList.add(UserResponse.builder().message("User name already exists").build());
			}
		}
		return userResponseList;
	}

	@Override
	public UserResponse deleteStudent(String userName) {
		log.info("Deleting student with UserName: {}", userName);
		Optional<User> student = repository.findById(userName);
		if (student.isPresent() && student.get().getUserType().equals("student")) {
			repository.delete(student.get());

			return UserResponse.builder().user(student.get()).message("Student deleted successfully").build();
		}
		return UserResponse.builder().message("No student found").build();
	}

	@Override
	public UserResponse deleteTeacher(String userName) {
		log.info("Deleting teacher with UserName: {}", userName);
		Optional<User> teacher = repository.findById(userName);
		if (teacher.isPresent() && teacher.get().getUserType().equals("teacher")) {
			repository.delete(teacher.get());

			return UserResponse.builder().message("Teacher deleted successfully").user(teacher.get()).build();
		}
		return UserResponse.builder().message("No teacher found").build();
	}

	@Override
	public UserResponse modifyStudent(User user) {
		log.info("Modifying student: {}", user);
		user.setUserType("student");
		Optional<User> student = repository.findById(user.getId());
		if (student.isPresent() && student.get().getUserType().equals("student")) {
			int totalMarks = user.getTotalMarks();

			if (totalMarks >= 450) {
				user.setGrade('A');
			} else if (totalMarks >= 350 && totalMarks <= 450) {
				user.setGrade('B');
			} else {
				user.setGrade('C');
			}
			repository.save(user);
			return UserResponse.builder().message("Student updated successfully")
					.user(user).build();
		}
		return UserResponse.builder().message("No student found").build();
	}

	@Override
	public UserResponse modifyTeacher(User user) {
		log.info("Modifying teacher: {}", user);
		user.setUserType("teacher");
		Optional<User> teacher = repository.findById(user.getId());
		if (teacher.isPresent() && teacher.get().getUserType().equals("teacher")) {
			repository.save(user);
			return UserResponse.builder().user(user).message("Teacher updated successfully").build();
		}
		return UserResponse.builder().message("No teacher found").build();
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
	public ResponseEntity<List<UserResponse>> fetchTeachers(String identifierType, String identifierValue) {
		log.info("Fetching teachers");
		List<UserResponse> resultList = new ArrayList<>();

		Query query = new Query();
		query.addCriteria(Criteria.where(identifierType).is(identifierValue));
		List<User> foundList = mongoTemplate.find(query, User.class);

		for (User user : foundList) {
			if (user.getUserType().equals("teacher")) {
				resultList.add(UserResponse.builder().user(user).message("Teacher fetched successfully").build());
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
		} else if(resultStudents.size()!=0) {			
			return resultStudents;
		}
		return null;
	}

	@Override
	public List<UserResponse> fetchAllTeachers() {
		log.info("Fetching all the teachers");
		List<UserResponse> resultTeachers = new ArrayList<>();
		List<User> foundUsers = repository.findByUserType("student");

		if(foundUsers.size()!=0) {			
			for (User teacher : foundUsers) {
				resultTeachers.add(UserResponse.builder().user(teacher).message("Teacher fetched successfully").build());
			}
		}
		if(resultTeachers.size()!=0) {			
			return resultTeachers;
		}
		return null;
	}
}
