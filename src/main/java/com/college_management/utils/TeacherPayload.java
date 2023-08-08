package com.college_management.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherPayload {
	private String id;
	private String name;
	private String dob;
	private String city;
	private String gender;
	private String password;
	private String userType;
	private double salary;
}
