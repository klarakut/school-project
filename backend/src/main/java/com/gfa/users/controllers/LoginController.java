package com.gfa.users.controllers;

import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.LoginRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.InvalidLoginCredentialsExpcetion;
import com.gfa.users.exceptions.PasswordMissingException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private final LoginService loginService;

  @Autowired
  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<? extends ResponseDto> login(@RequestBody LoginRequestDto dto) {
    try {
      StatusResponseDto response = loginService.login(dto);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (RequestBodyMissingException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid input"),HttpStatus.resolve(400));
    } catch (EmailMissingException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Email missing"),HttpStatus.resolve(400));
    } catch (PasswordMissingException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Password missing"),HttpStatus.resolve(400));
    } catch (InvalidEmailException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Wrong password"),HttpStatus.resolve(400));
    } catch (InvalidLoginCredentialsExpcetion e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid login details"),HttpStatus.resolve(401));
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"),HttpStatus.resolve(500));
    }
  }
}
