package com.college_management.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {
	private StudentPayload student;
	private String message;
}
