package com.vibeshare.model;

public class LoginResponse {

	 private String message;
	    private String userId;

	    public LoginResponse(String message, String userId) {
	        this.message = message;
	        this.userId = userId;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }
}
