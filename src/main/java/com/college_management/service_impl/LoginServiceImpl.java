package com.college_management.service_impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college_management.model.User;
import com.college_management.repository.UserRepository;
import com.college_management.service.LoginService;
import com.college_management.utils.AdminResponse;
import com.college_management.utils.CollegeUtils;
import com.college_management.utils.StudentResponse;
import com.college_management.utils.TeacherResponse;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository repository;

	@Override
	public AdminResponse loginAdmin(String userName, String password) {
		log.info("Logging in as admin");
		Optional<User> user=repository.findById(userName);
		if(user.isPresent() && user.get().getUserType().equals("admin")) {

			if(user.get().getPassword().equals(password)) {
				AdminResponse admin = CollegeUtils.userToAdminConverter(user.get());
				admin.getAdmin().setUserType("admin");
				return AdminResponse.builder().message("Admin Login Successfull").admin(admin.getAdmin()).build();
			}else {
				return AdminResponse.builder().message("Incorrect Password").build();
			}
		}else {
			return AdminResponse.builder().message("No admin found").build();
		}
	}

	@Override
	public StudentResponse loginStudent(String userName, String password, HttpSession session) {
		log.info("Logging in as student");
		Optional<User> user=repository.findById(userName);
		if(user.isPresent() && user.get().getUserType().equals("student")) {

			if(user.get().getPassword().equals(password)) {
				StudentResponse student = CollegeUtils.userToStudentConverter(user.get());
				session.setAttribute("student", student);
				return StudentResponse.builder().message("Student Login Successfull").student(student.getStudent()).build();
			}else {
				return StudentResponse.builder().message("Incorrect Password").build();
			}
		}else {
			return StudentResponse.builder().message("No student found").build();
		}
	}

	@Override
	public TeacherResponse loginTeacher(String userName, String password) {
		log.info("Logging in as teacher");
		Optional<User> user=repository.findById(userName);
		if(user.isPresent() && user.get().getUserType().equals("teacher")) {

			if(user.get().getPassword().equals(password)) {
				TeacherResponse teacher = CollegeUtils.userToTeacherConverter(user.get());
				return TeacherResponse.builder().message("Teacher Login Successfull").teacher(teacher.getTeacher()).build();
			}else {
				return TeacherResponse.builder().message("Incorrect Password").build();
			}
		}else {
			return TeacherResponse.builder().message("No teacher found").build();
		}
	}
}
