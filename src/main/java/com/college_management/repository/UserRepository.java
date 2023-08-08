package com.college_management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.college_management.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
