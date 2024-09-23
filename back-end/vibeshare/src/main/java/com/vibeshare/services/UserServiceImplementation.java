package com.vibeshare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vibeshare.model.User;
import com.vibeshare.repository.UserRepo;

@Service
public class UserServiceImplementation implements UserService {

	 @Autowired
	    private UserRepo userRepo;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Override
	    public String registerUser(User user) {
	        boolean emailExists = userRepo.existsByEmail(user.getEmail());
	        boolean usernameExists = userRepo.existsByUsername(user.getUsername());

	        if (emailExists && usernameExists) {
	            return "Email and Username already in use!";
	        } else if (emailExists) {
	            return "Email already in use!";
	        } else if (usernameExists) {
	            return "Username already in use!";
	        }

	        // Encode the password before saving the user
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepo.save(user);
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

	    @Override
	    public boolean updateUserPassword(String userId, String newPassword) {
	        Optional<User> userOptional = userRepo.findById(userId);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            user.setPassword(passwordEncoder.encode(newPassword));
	            userRepo.save(user);
	            return true;
	        }
	        return false;
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
	
}