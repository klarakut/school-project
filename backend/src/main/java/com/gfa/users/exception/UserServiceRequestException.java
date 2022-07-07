package com.gfa.users.exception;

public class UserServiceRequestException extends RuntimeException{

    public UserServiceRequestException(String message) {
        super(message);
    }

    public UserServiceRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
