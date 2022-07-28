package com.gfa.users.controllers;


import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.exceptions.EmptyBodyException;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.exceptions.AlreadyVerifiedException;
import com.gfa.users.exceptions.DuplicateRoleException;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.IdNotFoundException;
import com.gfa.users.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidInputException;
import com.gfa.users.exceptions.InvalidLoginCredentialsException;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PasswordMissingException;
import com.gfa.users.exceptions.PasswordTooShortException;
import com.gfa.users.exceptions.PermissionExistsException;
import com.gfa.users.exceptions.PermissionIdNotFoundException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.exceptions.RoleExistsException;
import com.gfa.users.exceptions.RoleNotFoundException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.ShortUsernameException;
import com.gfa.users.exceptions.TeamExistsException;
import com.gfa.users.exceptions.TeamNotFoundException;
import com.gfa.users.exceptions.UnexpectedErrorException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.exceptions.UserExistsException;
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

  @ExceptionHandler(PermissionExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponseDto handlePermissionExistsException() {
    return new ErrorResponseDto("Ability already exist");
  }

  @ExceptionHandler(PermissionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDto handlePermissionNotFoundException() {
    return new ErrorResponseDto("Permission not found");
  }

  @ExceptionHandler(EmptyBodyException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDto handleEmptyBodyException() {
    return new ErrorResponseDto("Role is required");
  }

  @ExceptionHandler(DuplicateRoleException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponseDto handleDuplicateRoleException() {
    return new ErrorResponseDto("Role already exists");
  }

  @ExceptionHandler(IdNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDto handleIdNotFoundException() {
    return new ErrorResponseDto("Role not found");
  }

  @ExceptionHandler(InvalidInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidInputException() {
    return new ErrorResponseDto("Invalid data");
  }

  @ExceptionHandler(PermissionIdNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handlePermissionIdNotFoundException() {
    return new ErrorResponseDto("Permission not found");
  }

  @ExceptionHandler(TeamExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleTeamExistsException() {
    return new ErrorResponseDto("Team is already exist");
  }

  @ExceptionHandler(TeamNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleTeamNotFoundException() {
    return new ErrorResponseDto("Team not found");
  }

  @ExceptionHandler(UserExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUserExistsException() {
    return new ErrorResponseDto("User already exist in team");
  }

  @ExceptionHandler(RoleNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleRoleNotFoundException() {
    return new ErrorResponseDto("Role not found");
  }

  @ExceptionHandler(RoleExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleRoleExistsException() {
    return new ErrorResponseDto("Role exist in team");
  }
}
