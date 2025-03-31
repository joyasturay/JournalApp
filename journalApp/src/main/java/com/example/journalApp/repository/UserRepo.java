package com.example.journalApp.repository;

import com.example.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    @Query("{ 'userName' : ?0 }") // Ensures it searches by the exact MongoDB field
    Optional<User> findByUserName(String userName);
}