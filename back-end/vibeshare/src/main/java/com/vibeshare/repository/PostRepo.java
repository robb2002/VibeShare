package com.vibeshare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vibeshare.model.Post;

import java.util.List;

@Repository
public interface PostRepo extends MongoRepository<Post, String> {

    // Find all posts by a user (using their userId)
    List<Post> findByUserId(String userId);

    // Find all posts containing a specific text or content
    List<Post> findByContentContaining(String keyword);
}

