package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.exceptions.EmailAlreadyVerifiedException;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.services.DatabaseEmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailVerificationController {

  private final DatabaseEmailVerificationService service;

  @Autowired
  public EmailVerificationController(DatabaseEmailVerificationService service) {
    this.service = service;
  }

  @GetMapping("/email/verify/{token}")
  public ResponseEntity<? extends ResponseDto> verify(
      @PathVariable(required = false) String token) {
    try {
      return new ResponseEntity<>(service.verify(token), HttpStatus.OK);
    } catch (InvalidTokenException ignored) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid token"), HttpStatus.BAD_REQUEST);
    } catch (TokenExpiredException ignored) {
      return new ResponseEntity<>(new ErrorResponseDto("Expired token"), HttpStatus.BAD_REQUEST);
    } catch (EmailAlreadyVerifiedException ignored) {
      return new ResponseEntity<>(
          new ErrorResponseDto("This email has already been verified"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/email/resend")
  public ResponseEntity<? extends ResponseDto> resend(
      @RequestBody(required = false) EmailRequestDto dto) {
    try {
      return new ResponseEntity<>(service.resend(dto), HttpStatus.OK);
    } catch (RequestBodyMissingException ignored) {
      return new ResponseEntity<>(new ErrorResponseDto("No request body"), HttpStatus.BAD_REQUEST);
    } catch (EmailMissingException ignored) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Email is required"), HttpStatus.BAD_REQUEST);
    } catch (UserNotFoundException ignored) {
      return new ResponseEntity<>(
          new ErrorResponseDto("There is no user with the specified email"),
          HttpStatus.BAD_REQUEST);
    }
  }
}
