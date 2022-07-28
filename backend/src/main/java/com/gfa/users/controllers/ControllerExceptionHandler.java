package com.gfa.users.controllers;


import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PasswordTooShortException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(InvalidIdException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidIdException() {
    return new ErrorResponseDto("Invalid id");
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUserNotFoundException() {
    return new ErrorResponseDto("User not found");
  }

  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidRequestException() {
    return new ErrorResponseDto("Invalid data");
  }

  @ExceptionHandler(UnknownErrorException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUnknownErrorException() {
    return new ErrorResponseDto("Server error");
  }

  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUInvalidRequestException() {
    return new ErrorResponseDto("Invalid data");
  }

  @ExceptionHandler(InvalidPasswordException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidPasswordException() {
    return new ErrorResponseDto("Invalid password!");
  }

  @ExceptionHandler(InvalidTokenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidTokenException() {
    return new ErrorResponseDto("Invalid token!");
  }

  @ExceptionHandler(TokenExpiredException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleTokenExpiredException() {
    return new ErrorResponseDto("Expired token!");
  }

  @ExceptionHandler(PasswordTooShortException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handlePasswordTooShortException() {
    return new ErrorResponseDto("Password must be at least 8 characters long");
  }

  @ExceptionHandler(InvalidEmailException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidEmailException() {
    return new ErrorResponseDto("Invalid email");
  }




}
