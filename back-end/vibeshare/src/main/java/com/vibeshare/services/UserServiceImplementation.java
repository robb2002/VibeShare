package com.vibeshare.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vibeshare.model.User;
import com.vibeshare.repository.UserRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

	 @Autowired
	    private UserRepo userRepo;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    private static final String PROFILE_PICTURE_DIR = "profile_pictures/";

	    
	    @Override 
	    
	    public String registerUser(User user) {
	        // Check if the email already exists
	        boolean emailExists = userRepo.existsByEmail(user.getEmail());

	        // Check if the username already exists
	        boolean usernameExists = userRepo.existsByUsername(user.getUsername());

	        // Check if both email and username already exist
	        if (emailExists && usernameExists) {
	            return "Both email and username are already in use. Please try again with different credentials.";
	        }
	        // Check if email exists
	        else if (emailExists) {
	            return "Email is already in use. Please try with a different email.";
	        }
	        // Check if username exists
	        else if (usernameExists) {
	            return "Username is already in use. Please try with a different username.";
	        }

	        // If no conflicts, proceed with encoding the password and saving the user
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepo.save(user);
	        
	        // Return a success message upon successful registration
	        return "User registered successfully!";
	    }
	    @Override
	    public String loginUser(String usernameOrEmail, String password) {
	        Optional<User> userOptional = userRepo.findByEmail(usernameOrEmail);
	        if (!userOptional.isPresent()) {
	            userOptional = userRepo.findByUsername(usernameOrEmail);
	        }

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            if (passwordEncoder.matches(password, user.getPassword())) {
	                return "Login successful!";
	            } else {
	                return "Invalid password!";
	            }
	        } else {
	            return "User not found!";
	        }
	    }

	    @Override
	    public User saveUser(User user) {
	        return userRepo.save(user);
	    }

	    @Override
	    public Optional<User> getUserByEmail(String email) {
	        return userRepo.findByEmail(email);
	    }

	    @Override
	    public Optional<User> getUserByUsernameAndEmail(String username, String email) {
	        return userRepo.findByUsernameAndEmail(username, email);
	    }

	    @Override
	    public Optional<User> getUserById(String userId) {
	        return userRepo.findById(userId);
	    }

	    @Override
	    public void deleteUserById(String userId) {
	        userRepo.deleteById(userId);
	    }

	    public String updateUserPassword(String id, String newPassword) {
	        Optional<User> optionalUser = userRepo.findById(id);

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();

	            // Fetch the current encrypted password
	            String currentPassword = user.getPassword();

	            // Check if the new password matches the old one
	            if (passwordEncoder.matches(newPassword, currentPassword)) {
	                return "New password cannot be the same as the old password!";
	            }

	            // If validation passes, encrypt and update the password
	            user.setPassword(passwordEncoder.encode(newPassword));
	            userRepo.save(user);
	            return "Password updated successfully!";
	        } else {
	            return "User not found!";
	        }
	    }

	    @Override
	    public List<User> findUsersByPartialUsername(String partialUsername) {
	        return userRepo.findByUsernameContaining(partialUsername);
	    }

	    @Override
	    public boolean activateUser(String userId) {
	        Optional<User> userOptional = userRepo.findById(userId);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            user.setActive(true);
	            userRepo.save(user);
	            return true;
	        }
	        return false;
	    }

	    @Override
	    public boolean deactivateUser(String userId) {
	        Optional<User> userOptional = userRepo.findById(userId);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            user.setActive(false);
	            userRepo.save(user);
	            return true;
	        }
	        return false;
	    }

	    @Override
	    public boolean emailExists(String email) {
	        return userRepo.existsByEmail(email);
	    }

	    @Override
	    public boolean usernameExists(String username) {
	        return userRepo.existsByUsername(username);
	    }

	    @Override
	    public Page<User> getUsersWithPagination(Pageable pageable) {
	        return userRepo.findAll(pageable);
	    }
	    
	    @Override
	    public String updateUserProfile(String id, String username, String email, String bio, String profilePicture, String password, String location) {
	        Optional<User> userOptional = userRepo.findById(id);
	        
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();

	            // Update the fields
	            if (username != null && !username.isEmpty()) {
	                user.setUsername(username);
	            }
	            if (email != null && !email.isEmpty()) {
	                user.setEmail(email);
	            }
	            if (bio != null && !bio.isEmpty()) {
	                user.setBio(bio);
	            }
	            if (profilePicture != null && !profilePicture.isEmpty()) {
	                user.setProfilePicture(profilePicture);
	            }
	            if (password != null && !password.isEmpty()) {
	                user.setPassword(passwordEncoder.encode(password)); // Encrypt the password
	            }
	            if (location != null && !location.isEmpty()) {
	                user.setLocation(location);  // Set location
	            }

	            userRepo.save(user);
	            return "Profile updated successfully!";
	        } else {
	            return "User not found.";
	        }
	    }

	    @Override
	    public String uploadProfilePicture(String id, MultipartFile profilePicture) throws IOException {
	        Optional<User> userOptional = userRepo.findById(id);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            String fileName = id + "_" + profilePicture.getOriginalFilename();
	            String filePath = PROFILE_PICTURE_DIR + fileName;
	            
	            // Save file locally
	            File directory = new File(PROFILE_PICTURE_DIR);
	            if (!directory.exists()) {
	                directory.mkdirs(); // Create directory if it doesn't exist
	            }
	            Files.write(Paths.get(filePath), profilePicture.getBytes());
	            
	            // Save file path in the user profile
	            user.setProfilePicture(filePath);
	            userRepo.save(user);

	            return "Profile picture uploaded successfully!";
	        }
	        return "User not found!";
	    }
	    
	    private String saveProfilePicture(byte[] pictureData, String userId) {
	        // Logic to save image (e.g., to AWS S3, or locally)
	        // Return the URL/path to the saved image
	        return "https://image-storage-service/" + userId + "/profile.jpg"; // Example URL
	    }
		
	    
	    public String getUserIdByUsernameOrEmail(String usernameOrEmail) {
	    	  Optional<User> userOptional = userRepo.findByEmail(usernameOrEmail);
	    	    
	    	    if (!userOptional.isPresent()) {
	    	        userOptional = userRepo.findByUsername(usernameOrEmail);
	    	    }
	    	    
	    	    return userOptional.map(User::getId).orElse(null);
	       
	    }
		
	
}