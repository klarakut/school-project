package com.gfa.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;


public class UserServiceExceptionHandler {
    public ResponseEntity <Object> handleUserServiceException(UserServiceRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        UserServiceException userServiceException = new UserServiceException(
            e.getMessage(),
            e,
            HttpStatus.BAD_REQUEST,
            ZonedDateTime.now(ZoneId.of("Z"))
    );
    return new ResponseEntity<>(userServiceException, badRequest);
    }
}
