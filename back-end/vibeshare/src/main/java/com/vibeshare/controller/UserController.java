package com.vibeshare.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vibeshare.model.User;
import com.vibeshare.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
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
