package com.vibeshare.services;

import com.vibeshare.model.Post;
import com.vibeshare.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(String userId, Post post) {
        post.setCreatedAt(new Date());  // Set creation date
        post.setUserId(userId);  // Set the user who created the post
        return postRepository.save(post);  // Save the post to MongoDB
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();  // Retrieve all posts
    }

    @Override
    public Post getPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByUserId(userId);  // Get all posts by a user
    }

    @Override
    public Post updatePost(String userId, String postId, Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent() && existingPost.get().getUserId().equals(userId)) {
            Post post = existingPost.get();
            post.setContent(updatedPost.getContent());
            post.setImageUrl(updatedPost.getImageUrl());
            post.setUpdatedAt(new Date());  // Set the update date
            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found or user is unauthorized to update this post");
        }
    }

    @Override
    public void deletePost(String userId, String postId) {
        Optional<Post> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent() && existingPost.get().getUserId().equals(userId)) {
            postRepository.deleteById(postId);  // Delete post by ID
        } else {
            throw new RuntimeException("Post not found or user is unauthorized to delete this post");
        }
    }

    @Override
    public void likePost(String postId, String userId) {
        Post post = getPostById(postId);
        if (!post.getLikes().contains(userId)) {
            post.getLikes().add(userId);  // Add like if user hasn't liked it yet
            postRepository.save(post);
        }
    }

    @Override
    public void unlikePost(String postId, String userId) {
        Post post = getPostById(postId);
        if (post.getLikes().contains(userId)) {
            post.getLikes().remove(userId);  // Remove like if user has liked it
            postRepository.save(post);
        }
    }

    @Override
    public void addComment(String postId, String commentId) {
        Post post = getPostById(postId);
        post.getComments().add(commentId);  // Add comment ID to the post
        postRepository.save(post);
    }

    @Override
    public void removeComment(String postId, String commentId) {
        Post post = getPostById(postId);
        post.getComments().remove(commentId);  // Remove comment ID from the post
        postRepository.save(post);
    }
}
