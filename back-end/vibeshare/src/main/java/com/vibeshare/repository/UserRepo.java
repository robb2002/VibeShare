package com.vibeshare.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vibeshare.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    
    // Find a user by email
    Optional<User> findByEmail(String email);
    
    // Find a user by both username and email
    Optional<User> findByUsernameAndEmail(String username, String email);
    
    Optional<User> findByUsername(String username);
    
    List<User> findByUsernameContaining(String partialUsername);
}

