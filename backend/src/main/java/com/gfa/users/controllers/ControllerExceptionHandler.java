package com.gfa.users.controllers;


import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.exceptions.AlreadyVerifiedException;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidLoginCredentialsException;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PasswordMissingException;
import com.gfa.users.exceptions.PasswordTooShortException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.ShortUsernameException;
import com.gfa.users.exceptions.UnexpectedErrorException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.exceptions.UsernameMissingException;
import com.gfa.users.exceptions.UsernameTakenException;
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
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponseDto handleUnknownErrorException() {
    return new ErrorResponseDto("Server error");
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

  @ExceptionHandler(UnverifiedEmailException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUnverifiedEmailException() {
    return new ErrorResponseDto("Unverified email!");
  }

  @ExceptionHandler(AlreadyVerifiedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleAlreadyVerifiedException() {
    return new ErrorResponseDto("Email already verified!");
  }

  @ExceptionHandler(UsernameMissingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUsernameMissingException() {
    return new ErrorResponseDto("Username is required");
  }

  @ExceptionHandler(PasswordMissingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handlePasswordMissingException() {
    return new ErrorResponseDto("Password is required");
  }

  @ExceptionHandler(UsernameTakenException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponseDto handleUsernameTakenException() {
    return new ErrorResponseDto("Username is already taken");
  }

  @ExceptionHandler(ShortUsernameException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleShortUsernameException() {
    return new ErrorResponseDto("Username must be at least 4 characters long");
  }

  @ExceptionHandler(ShortPasswordException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleShortPasswordException() {
    return new ErrorResponseDto("Password must be at least 8 characters long");
  }

  @ExceptionHandler(UnexpectedErrorException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUnexpectedErrorException() {
    return new ErrorResponseDto("Unknown error");
  }

  @ExceptionHandler(RequestBodyMissingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleRequestBodyMissingException() {
    return new ErrorResponseDto("Invalid input");
  }

  @ExceptionHandler(EmailMissingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleEmailMissingException() {
    return new ErrorResponseDto("Email missing");
  }

  @ExceptionHandler(InvalidLoginCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponseDto handleInvalidLoginCredentialsException() {
    return new ErrorResponseDto("Invalid login details");
  }
}
