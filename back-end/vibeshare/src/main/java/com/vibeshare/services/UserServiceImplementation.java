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
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
    	 System.out.println("Saving user: " + user);
    	 user.setPassword(passwordEncoder.encode(user.getPassword()));
       
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
