package com.vibeshare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vibeshare.model.User;
import com.vibeshare.repository.UserRepo;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepo userRepo;
    
    // Register a new user
    public String registerUser(User user) {
        // Check if email or username already exists
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return "Email already in use!";
        }

        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return "Username already taken!";
        }

        // Validate password strength (this can be customized)
        if (user.getPassword().length() < 6) {
            return "Password must be at least 6 characters!";
        }

        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        // Save user to the database
        userRepo.save(user);
        return "User registered successfully!";
    }

    public String loginUser(String usernameOrEmail, String password) {
        User user = userRepo.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
            .orElse(null);

        if (user == null) {
            return "User not found!";
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            return "Invalid username or password!";
        }

        return "Login successful!";
    }

    
    @Override
    public User saveUser(User user) {
        System.out.println("Saving user: " + user);
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
            // Hash the password before saving
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
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
            user.setActive(true); // Ensure this field exists in User model
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
            user.setActive(false); // Ensure this field exists in User model
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @Override
    public Page<User> getUsersWithPagination(Pageable pageable) {
        return userRepo.findAll(pageable);
    }
}
