package com.vibeshare.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vibeshare.model.LoginResponse;
import com.vibeshare.model.Post;
import com.vibeshare.model.User;
import com.vibeshare.services.PostService;
import com.vibeshare.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String result = userService.registerUser(user);
        return result.equals("User registered successfully!") 
                ? ResponseEntity.ok(result) 
                : ResponseEntity.badRequest().body(result);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> loginData) {
        String usernameOrEmail = loginData.get("username");
        if (usernameOrEmail == null) {
            usernameOrEmail = loginData.get("email");
        }
        String password = loginData.get("password");

        if (usernameOrEmail == null || password == null) {
            return ResponseEntity.badRequest().body(new LoginResponse("Username/Email and Password must be provided.", null));
        }

        String result = userService.loginUser(usernameOrEmail, password);
        if (result.equals("Login successful!")) {
            String userId = userService.getUserIdByUsernameOrEmail(usernameOrEmail);
            return ResponseEntity.ok(new LoginResponse(result, userId));
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse(result, null));
        }
    }

 // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty() 
                ? ResponseEntity.noContent().build()  // 204 No Content if no users found
                : ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.isPresent() 
                ? ResponseEntity.ok(user) 
                : ResponseEntity.notFound().build();
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // Get user by username and email
    @GetMapping("/username/{username}/email/{email}")
    public ResponseEntity<Optional<User>> getUserByUsernameAndEmail(@PathVariable String username, @PathVariable String email) {
        Optional<User> user = userService.getUserByUsernameAndEmail(username, email);
        return ResponseEntity.ok(user);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // Update user password
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updateUserPassword(
        @PathVariable String id, 
        @RequestBody Map<String, String> passwordData) {
        
        String newPassword = passwordData.get("newPassword");
        String result = userService.updateUserPassword(id, newPassword);

        return result.equals("Password updated successfully!") 
                ? ResponseEntity.ok(result) 
                : ResponseEntity.badRequest().body(result);
    }

    // Search users by partial username
    @GetMapping("/search")
    public ResponseEntity<List<User>> findUsersByPartialUsername(@RequestParam String partialUsername) {
        List<User> users = userService.findUsersByPartialUsername(partialUsername);
        return ResponseEntity.ok(users);
    }

    // Activate user
    @PutMapping("/{id}/activate")
    public ResponseEntity<Boolean> activateUser(@PathVariable String id) {
        boolean activated = userService.activateUser(id);
        return ResponseEntity.ok(activated);
    }

    // Deactivate user
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Boolean> deactivateUser(@PathVariable String id) {
        boolean deactivated = userService.deactivateUser(id);
        return ResponseEntity.ok(deactivated);
    }

    // Check if email exists
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    // Pagination for users
    @GetMapping
    public ResponseEntity<Page<User>> getUsersWithPagination(Pageable pageable) {
        Page<User> users = userService.getUsersWithPagination(pageable);
        return ResponseEntity.ok(users);
    }

    // Update user profile
    @PutMapping("/{id}/profile")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable String id,
            @RequestBody Map<String, String> profileData) {

        String username = profileData.get("username");
        String email = profileData.get("email");
        String bio = profileData.get("bio");
        String profilePicture = profileData.get("profilePicture");
        String password = profileData.get("password");
        String location = profileData.get("location");  // Added location field

        String result = userService.updateUserProfile(id, username, email, bio, profilePicture, password, location);

        return result.equals("Profile updated successfully!") 
                ? ResponseEntity.ok(result) 
                : ResponseEntity.badRequest().body(result);
    }

    // Upload profile picture
    @PostMapping("/{id}/uploadProfilePicture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable String id,
            @RequestParam("profilePicture") MultipartFile profilePicture) {
        try {
            String result = userService.uploadProfilePicture(id, profilePicture);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading profile picture: " + e.getMessage());
        }
    }

    // Retrieve user profile
    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create post and associate it with user
    @PostMapping("/{id}/posts")
    public ResponseEntity<String> createPost(
            @PathVariable String id,
            @RequestBody Post post) {
        // Call the service and pass the userId (id) and post object
        Post createdPost = postService.createPost(id, post);
        
        // Check if the post is created successfully
        if (createdPost != null) {
            return ResponseEntity.ok("Post created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to create post.");
        }
    }

    // Retrieve all posts by a user
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable String id) {
        List<Post> posts = postService.getPostsByUserId(id);
        return posts.isEmpty() 
                ? ResponseEntity.noContent().build()  // Return 204 No Content if no posts found
                : ResponseEntity.ok(posts);
    }
}
