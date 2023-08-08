package com.college_management.utils;

import com.college_management.model.User;

public class CollegeUtils {

	public static User userConverter(StudentPayload student) {

		return User.builder()
				.name(student.getName())
				.city(student.getCity())
				.dob(student.getDob())
				.gender(student.getGender())
				.id(student.getId())
				.totalMarks(student.getTotalMarks())
				.grade(student.getGrade())
				.password(student.getPassword())
				.userType("student")
				.build();
	}

	public static StudentResponse userToStudentConverter(User student) {

		 StudentPayload build = StudentPayload.builder()
				.name(student.getName())
				.city(student.getCity())
				.dob(student.getDob())
				.gender(student.getGender())
				.id(student.getId())
				.totalMarks(student.getTotalMarks())
				.grade(student.getGrade())
				.password(student.getPassword())
				.userType("student")
				.build();

		 return StudentResponse.builder().student(build).build();
	}

	public static User userConverter(TeacherPayload teacher) {
		return User.builder()
				.name(teacher.getName())
				.city(teacher.getCity())
				.dob(teacher.getDob())
				.gender(teacher.getGender())
				.id(teacher.getId())
				.salary(teacher.getSalary())
				.password(teacher.getPassword())
				.userType("teacher")
				.build();
	}

	public static TeacherResponse userToTeacherConverter(User teacher) {
		 TeacherPayload build = TeacherPayload.builder()
				.name(teacher.getName())
				.city(teacher.getCity())
				.dob(teacher.getDob())
				.gender(teacher.getGender())
				.id(teacher.getId())
				.salary(teacher.getSalary())
				.password(teacher.getPassword())
				.userType("teacher")
				.build();

		 return TeacherResponse.builder().teacher(build).build();
	}
	
	public static AdminResponse userToAdminConverter(User teacher) {
		AdminPayload build = AdminPayload.builder()
				.name(teacher.getName())
				.city(teacher.getCity())
				.dob(teacher.getDob())
				.gender(teacher.getGender())
				.id(teacher.getId())
				.salary(teacher.getSalary())
				.password(teacher.getPassword())
				.userType("teacher")
				.build();
		
		return AdminResponse.builder().admin(build).build();
	}
	

}
