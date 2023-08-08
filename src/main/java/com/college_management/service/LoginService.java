package com.college_management.service;

import org.springframework.stereotype.Service;
import com.college_management.utils.AdminResponse;
import com.college_management.utils.StudentResponse;
import com.college_management.utils.TeacherResponse;

import jakarta.servlet.http.HttpSession;

@Service
public interface LoginService {

	AdminResponse loginAdmin(String userName, String password);
	StudentResponse loginStudent(String userName, String password, HttpSession session);
	TeacherResponse loginTeacher(String userName, String password);

}
