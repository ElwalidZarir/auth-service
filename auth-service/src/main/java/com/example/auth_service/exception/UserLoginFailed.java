package com.example.auth_service.exception;

public class UserLoginFailed extends Exception{ 
    public UserLoginFailed(String message){
        super(message);
    }
}
