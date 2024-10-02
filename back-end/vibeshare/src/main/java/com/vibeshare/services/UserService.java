package com.vibeshare.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.vibeshare.model.Post;
import com.vibeshare.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

	// Register a new user
    String registerUser(User user);

    // Login a user
    String loginUser(String usernameOrEmail, String password);

    List<User> getAllUsers();
    // Save or update a user
    User saveUser(User user);

    // Find a user by email
    Optional<User> getUserByEmail(String email);

    // Find a user by both username and email
    Optional<User> getUserByUsernameAndEmail(String username, String email);

    // Find a user by ID
    Optional<User> getUserById(String userId);

    // Delete a user by ID
    void deleteUserById(String userId);

    // Update user password
    String updateUserPassword(String userId, String newPassword);

    // Find users by partial username
    List<User> findUsersByPartialUsername(String partialUsername);

    // Activate user account
    boolean activateUser(String userId);

    // Deactivate user account
    boolean deactivateUser(String userId);

    // Check if email exists
    boolean emailExists(String email);

    // Check if username exists
    boolean usernameExists(String username);

    // Get users with pagination
    Page<User> getUsersWithPagination(Pageable pageable);
    
    String updateUserProfile(String id, String username, String email, String bio, String profilePicture, String password, String location) ;
    
    String uploadProfilePicture(String id, MultipartFile profilePicture) throws IOException;
    
    String getUserIdByUsernameOrEmail(String usernameOrEmail);

	void addPostToUser(String userId, String postId);

	List<Post> getUserPosts(String userId);
}