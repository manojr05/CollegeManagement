package com.college_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "user")
@Data
@Builder
public class User {
	@Id
	private String id;
	private String name;
	private String dob;
	private String city;
	private String gender;
	private Integer totalMarks;
	private Character grade;
	private String password;
	private Double salary;
	private String userType;
}
