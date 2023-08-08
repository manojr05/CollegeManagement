package com.college_management.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminResponse {
	private AdminPayload admin;
	private String message;
}
