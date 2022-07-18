package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.common.exceptions.UnknownErrorException;
import com.gfa.users.dtos.CreateUserRequestDto;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.exceptions.*;
import com.gfa.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

         /* URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/register/{username}")
                .buildAndExpand(userResponse.username).toUri();

            return  ResponseEntity
                  .created(location)
                  .body(userResponse);*/

      return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
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
    catch (com.gfa.users.exceptions.InvalidEmailException e){
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

  @PostMapping("/reset-password")
  public ResponseEntity<? extends ResponseDto> resetPassword(
      @RequestBody EmailRequestDto emailDto) {
    try {
      StatusResponseDto dtoStatus = userService.resetPasswords(emailDto);
      return new ResponseEntity<>(dtoStatus, HttpStatus.OK);
    } catch (InvalidEmailException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST);
    } catch (UnverifiedEmailException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Unverified email!"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Something goes wrong"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<? extends ResponseDto> resetPassword(
      @RequestBody PasswordResetRequestDto passwordResetRequestDto, @PathVariable String token) {
    try {
      StatusResponseDto dtoStatus = userService.resetPassword(token, passwordResetRequestDto);
      return new ResponseEntity<>(dtoStatus, HttpStatus.OK);
    } catch (InvalidPasswordException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Invalid password!"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTokenException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid token!"), HttpStatus.BAD_REQUEST);
    } catch (TokenExpiredException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Expired token!"), HttpStatus.BAD_REQUEST);
    } catch (PasswordTooShortException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Password must be at least 8 characters long"),
          HttpStatus.BAD_REQUEST);
    }
  }
}
