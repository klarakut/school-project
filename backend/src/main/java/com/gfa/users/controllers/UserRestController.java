package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.dtos.UserCreateRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserPatchRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.services.UserService;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

  private final UserService userService;
  private final Environment environment;

  @Autowired
  public UserRestController(UserService userService, Environment environment) {
    this.userService = userService;
    this.environment = environment;
  }

  @PostMapping({"/register", "/users"})
  public ResponseEntity<? extends ResponseDto> store(@RequestBody UserCreateRequestDto dto) {

    UserResponseDto userResponse = userService.store(dto);

//    URI location = ServletUriComponentsBuilder
//        .fromCurrentContextPath().path("/register/{username}")
//        .buildAndExpand(userResponse.username).toUri();
//
//    return ResponseEntity
//        .created(location)
//        .body(userResponse);

    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);

  }

  @PostMapping("/email/verify/resend")
  public ResponseEntity<? extends ResponseDto> resendVerificationEmail(
      @RequestBody EmailRequestDto emailRequestDto) {

    StatusResponseDto dtoStatus = userService.resendVerificationEmail(emailRequestDto);
    return new ResponseEntity<>(dtoStatus, HttpStatus.OK);

  }

  @PostMapping("/reset-password")
  public ResponseEntity<? extends ResponseDto> resetPassword(
      @RequestBody EmailRequestDto emailDto) {

    StatusResponseDto dtoStatus = userService.resetPasswords(emailDto);
    return new ResponseEntity<>(dtoStatus, HttpStatus.OK);

  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<? extends ResponseDto> resetPassword(
      @RequestBody PasswordResetRequestDto passwordResetRequestDto, @PathVariable String token) {

    StatusResponseDto dtoStatus = userService.resetPassword(token, passwordResetRequestDto);
    return new ResponseEntity<>(dtoStatus, HttpStatus.OK);

  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDto>> index() {
    return new ResponseEntity(userService.index(), HttpStatus.OK);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable Long id) {

    UserResponseDto dtoResponse = userService.show(id);
    return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
  }


  @PatchMapping("/users/{id}")
  public ResponseEntity<? extends ResponseDto> update(
      @PathVariable Long id, @RequestBody UserPatchRequestDto userPatchRequestDto) {

    UserResponseDto dtoResponse = userService.update(id, userPatchRequestDto);
    return new ResponseEntity<>(dtoResponse, HttpStatus.OK);

  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable Long id) {

    EmptyResponseDto dtoResponse = userService.destroy(id);
    return new ResponseEntity<>(dtoResponse, HttpStatus.OK);

  }
}
