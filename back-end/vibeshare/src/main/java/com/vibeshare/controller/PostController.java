package com.vibeshare.controller;

import com.vibeshare.model.Post;
import com.vibeshare.model.User;
import com.vibeshare.services.PostService;
import com.vibeshare.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;  // To update the user's post list

    // Create a new post and associate it with the user
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam String userId, @RequestBody Post post) {
        // Create the post and associate it with the user
        Post createdPost = postService.createPost(userId, post);

        // Update the user model to add this post to their list of posts
        userService.addPostToUser(userId, createdPost.getId());

        return ResponseEntity.ok(createdPost);
    }

    // Get all posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Get a post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // Get posts by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable String userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    // Update a post (Only the user who created it can update)
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
        @PathVariable String id,
        @RequestParam String userId, 
        @RequestBody Post updatedPost) {
        
        Post post = postService.updatePost(userId, id, updatedPost);
        return ResponseEntity.ok(post);
    }

    // Delete a post (Only the user who created it can delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id, @RequestParam String userId) {
        postService.deletePost(userId, id);
        return ResponseEntity.noContent().build();
    }

    // Like a post
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePost(@PathVariable String id, @RequestParam String userId) {
        postService.likePost(id, userId);
        return ResponseEntity.ok().build();
    }

    // Unlike a post
    @PostMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikePost(@PathVariable String id, @RequestParam String userId) {
        postService.unlikePost(id, userId);
        return ResponseEntity.ok().build();
    }

    // Add a comment to a post
    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> addComment(@PathVariable String id, @RequestParam String commentId) {
        postService.addComment(id, commentId);
        return ResponseEntity.ok().build();
    }

    // Remove a comment from a post
    @DeleteMapping("/{id}/comment")
    public ResponseEntity<Void> removeComment(@PathVariable String id, @RequestParam String commentId) {
        postService.removeComment(id, commentId);
        return ResponseEntity.ok().build();
    }
}
