package com.vibeshare.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.vibeshare.model.User;
import com.vibeshare.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String result = userService.registerUser(user);
        System.out.println(user);
        if (result.equals("User registered successfully!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
        String usernameOrEmail = loginData.get("username");
        System.out.println(loginData);
        if (usernameOrEmail == null) {
            usernameOrEmail = loginData.get("email");  // If "username" is not provided, check "email"
        }
        String password = loginData.get("password");

        if (usernameOrEmail == null || password == null) {
            return ResponseEntity.badRequest().body("Username/Email and Password must be provided.");
        }

        String result = userService.loginUser(usernameOrEmail, password);
        if (result.equals("Login successful!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}/email/{email}")
    public ResponseEntity<Optional<User>> getUserByUsernameAndEmail(@PathVariable String username, @PathVariable String email) {
        Optional<User> user = userService.getUserByUsernameAndEmail(username, email);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Boolean> updateUserPassword(@PathVariable String id, @RequestParam String newPassword) {
        boolean updated = userService.updateUserPassword(id, newPassword);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> findUsersByPartialUsername(@RequestParam String partialUsername) {
        List<User> users = userService.findUsersByPartialUsername(partialUsername);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Boolean> activateUser(@PathVariable String id) {
        boolean activated = userService.activateUser(id);
        return ResponseEntity.ok(activated);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Boolean> deactivateUser(@PathVariable String id) {
        boolean deactivated = userService.deactivateUser(id);
        return ResponseEntity.ok(deactivated);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsersWithPagination(Pageable pageable) {
        Page<User> users = userService.getUsersWithPagination(pageable);
        return ResponseEntity.ok(users);
    }
}
