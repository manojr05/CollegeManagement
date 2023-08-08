package com.college_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CollegeManagementApplication {

	public static void main(String[] args) {
        log.info("Application started and ready to serve requests.");
        SpringApplication.run(CollegeManagementApplication.class, args);
	}
}
