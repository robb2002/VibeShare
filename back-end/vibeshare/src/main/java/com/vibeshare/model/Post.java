package com.vibeshare.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter@Getter
@Document(collection = "posts")
public class Post {
	    
	    @Id
	    private String id;
	    
	    private String content;
	    private String imageUrl;
	    private Date createdAt;
	    private Date UpdatedAt;
	    private String userId;  // The user who created the post
	    
	    private List<String> likes;  // List of user IDs who liked the post
	    private List<String> comments;  // List of comment IDs
	}