package com.college_management.utils;

import com.college_management.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	User user;
	String message;
}
