package com.vibeshare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String password;
    
    private String profilePicture; // URL of the profile picture
    
    private String bio; // User bio
    
    private String location; // Location (optional)
    
    private List<String> socialLinks; // Social media or external profile links
    
    private boolean isActive; // Whether the account is active
    
    private boolean isPrivate; // Whether the account is private (visibility control)

    private List<String> followers; // List of user IDs following this user
    
    private List<String> following; // List of user IDs this user is following

}
