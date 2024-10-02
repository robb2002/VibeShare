package com.vibeshare.services;

import com.vibeshare.model.Post;
import java.util.List;

public interface PostService {
    Post createPost(String userId, Post post);  // Create a new post with a user ID
    List<Post> getAllPosts();  // Get all posts
    Post getPostById(String postId);  // Get post by its ID
    List<Post> getPostsByUserId(String userId);  // Get all posts by a specific user
    Post updatePost(String userId, String postId, Post updatedPost);  // Update a post with authorization by user ID
    void deletePost(String userId, String postId);  // Delete a post with authorization by user ID
    void likePost(String postId, String userId);  // Like a post
    void unlikePost(String postId, String userId);  // Unlike a post
    void addComment(String postId, String commentId);  // Add a comment to a post
    void removeComment(String postId, String commentId);  // Remove a comment from a post
}
