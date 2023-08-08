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
import com.college_management.utils.CollegeUtils;
import com.college_management.utils.StudentPayload;
import com.college_management.utils.StudentResponse;
import com.college_management.utils.TeacherPayload;
import com.college_management.utils.TeacherResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

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
	public TeacherResponse addTeacher(TeacherPayload teacher) {
		log.info("Adding teacher: {}", teacher);
		if (repository.findById(teacher.getId()).isEmpty()) {
			teacher.setUserType("teacher");
			repository.save(CollegeUtils.userConverter(teacher));
			return TeacherResponse.builder().teacher(teacher).message("Teacher added successfully").build();
		}
		return TeacherResponse.builder().message("User name already exists").build();
	}

	@Override
	public List<TeacherResponse> addTeachers(List<TeacherPayload> teachers) {
		log.info("Adding multiple teachers: {}", teachers);
		List<TeacherResponse> userResponseList = new ArrayList<>();

		for (TeacherPayload teacher : teachers) {
			if (repository.findById(teacher.getId()).isEmpty()) {

				teacher.setUserType("teacher");
				repository.save(CollegeUtils.userConverter(teacher));

				userResponseList
						.add(TeacherResponse.builder().teacher(teacher).message("Teacher added successfully").build());
			} else {
				userResponseList.add(TeacherResponse.builder().message("User name already exists").build());
			}
		}
		return userResponseList;
	}

	@Override
	public StudentResponse deleteStudent(String userName) {
		log.info("Deleting student with UserName: {}", userName);
		Optional<User> student = repository.findById(userName);
		if (student.isPresent() && student.get().getUserType().equals("student")) {
			repository.delete(student.get());

			return StudentResponse.builder().student(CollegeUtils.userToStudentConverter(student.get()).getStudent()).message("Student deleted successfully").build();
		}
		return StudentResponse.builder().message("No student found").build();
	}

	@Override
	public TeacherResponse deleteTeacher(String userName) {
		log.info("Deleting teacher with UserName: {}", userName);
		Optional<User> teacher = repository.findById(userName);
		if (teacher.isPresent() && teacher.get().getUserType().equals("teacher")) {
			repository.delete(teacher.get());

			return TeacherResponse.builder().message("Teacher deleted successfully")
					.teacher(CollegeUtils.userToTeacherConverter(teacher.get()).getTeacher()).build();
		}
		return TeacherResponse.builder().message("No teacher found").build();
	}

	@Override
	public StudentResponse modifyStudent(StudentPayload user) {
		log.info("Modifying student: {}", user);
		user.setUserType("student");
		Optional<User> student = repository.findById(user.getId());
		if (student.isPresent() && student.get().getUserType().equals("student")) {
			int totalMarks = user.getTotalMarks();

			if (totalMarks > 450) {
				user.setGrade('A');
			} else if (totalMarks > 350 && totalMarks < 450) {
				user.setGrade('B');
			} else {
				user.setGrade('C');
			}
			repository.save(CollegeUtils.userConverter(user));
			return StudentResponse.builder().message("Student updated successfully")
					.student(user).build();
		}
		return StudentResponse.builder().message("No student found").build();
	}

	@Override
	public TeacherResponse modifyTeacher(TeacherPayload user) {
		log.info("Modifying teacher: {}", user);
		user.setUserType("teacher");
		Optional<User> teacher = repository.findById(user.getId());
		if (teacher.isPresent() && teacher.get().getUserType().equals("teacher")) {
			repository.save(CollegeUtils.userConverter(user));
			return TeacherResponse.builder().teacher(user).message("Teacher updated successfully").build();
		}
		return TeacherResponse.builder().message("No teacher found").build();
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
	public ResponseEntity<List<TeacherResponse>> fetchTeachers(String identifierType, String identifierValue) {
		log.info("Fetching teachers");
		List<TeacherResponse> resultList = new ArrayList<>();

		Query query = new Query();
		query.addCriteria(Criteria.where(identifierType).is(identifierValue));
		List<User> foundList = mongoTemplate.find(query, User.class);

		for (User user : foundList) {
			if (user.getUserType().equals("teacher")) {
				TeacherResponse userToTeacherConverter = CollegeUtils.userToTeacherConverter(user);
				userToTeacherConverter.setMessage("Teacher fetched successfully");
				resultList.add(userToTeacherConverter);
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

	@Override
	public List<TeacherResponse> fetchAllTeachers() {
		log.info("Fetching all the teachers");
		List<TeacherResponse> resultTeachers = new ArrayList<>();
		List<User> foundUsers = repository.findAll();

		for (User teacher : foundUsers) {
			if (teacher.getUserType().equals("teacher")) {
				TeacherResponse userToTeacherConverter = CollegeUtils.userToTeacherConverter(teacher);
				userToTeacherConverter.setMessage("Teacher fetched successfully");
				resultTeachers.add(userToTeacherConverter);
			}
		}
		return resultTeachers;
	}
}
