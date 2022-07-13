package com.gfa.users.controllers;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.UserResponseDto;
import com.gfa.users.exceptions.*;
import com.gfa.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserRestController {


    private final UserService userService;


    @Autowired
  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<? extends ResponseDto> store(@RequestBody CreateUserRequestDto dto) {
      try {
          UserResponseDto userResponse = userService.store(dto);

          URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/register/{username}")
                .buildAndExpand(userResponse.username).toUri();

           return ResponseEntity
                .created(location)
                .body(userResponse);

       }
       catch (UsernameMissingException e){
           return new ResponseEntity<>(new ErrorResponseDto("Username is required"),HttpStatus.BAD_REQUEST);
       }
       catch (PasswordMissingException e){
           return new ResponseEntity<>(new ErrorResponseDto("Password is required"),HttpStatus.BAD_REQUEST);
       }
       catch (EmailMissingException e){
           return new ResponseEntity<>(new ErrorResponseDto("Email is required"),HttpStatus.BAD_REQUEST);
       }
       catch (InvalidEmailException e){
           return new ResponseEntity<>(new ErrorResponseDto("Invalid email"),HttpStatus.BAD_REQUEST);
       }
       catch (UsernameTakenException e){
           return new ResponseEntity<>(new ErrorResponseDto("Username is already taken"),HttpStatus.CONFLICT);
       }
       catch (ShortUsernameException e){
           return new ResponseEntity<>(new ErrorResponseDto("Username must be at least 4 characters long"),HttpStatus.BAD_REQUEST);
       }
       catch (ShortPasswordException e){
           return new ResponseEntity<>(new ErrorResponseDto("Password must be at least 8 characters long"),HttpStatus.BAD_REQUEST);
       }
       catch (UnexpectedErrorException e){
           return new ResponseEntity<>(new ErrorResponseDto("Unknown error"),HttpStatus.BAD_REQUEST);
       }
  }
}

