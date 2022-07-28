package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.common.exceptions.UnknownErrorException;

import com.gfa.users.dtos.UserCreateRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserErrorResponseDto;
import com.gfa.users.dtos.UserPatchRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.exceptions.AlreadyVerifiedException;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PasswordMissingException;
import com.gfa.users.exceptions.PasswordTooShortException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.ShortUsernameException;
import com.gfa.users.exceptions.UnexpectedErrorException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.exceptions.UsernameMissingException;
import com.gfa.users.exceptions.UsernameTakenException;
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

      /* URI location = ServletUriComponentsBuilder
          .fromCurrentContextPath().path("/register/{username}")
          .buildAndExpand(userResponse.username).toUri();

      return  ResponseEntity
            .created(location)
            .body(userResponse);*/

      return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
//     catch (UsernameMissingException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Username is required"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (PasswordMissingException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Password is required"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (EmailMissingException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Email is required"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (com.gfa.users.exceptions.InvalidEmailException e) {
//      return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UsernameTakenException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Username is already taken"), HttpStatus.CONFLICT); HOTOVE
//    } catch (ShortUsernameException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Username must be at least 4 characters long"), HOTOVE
//          HttpStatus.BAD_REQUEST);
//    } catch (ShortPasswordException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Password must be at least 8 characters long"), HOTOVE
//          HttpStatus.BAD_REQUEST);
//    } catch (UnexpectedErrorException e) {
//      return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.BAD_REQUEST); HOTOVE
//    }
  }

  @PostMapping("/email/verify/resend")
  public ResponseEntity<? extends ResponseDto> resendVerificationEmail(
      @RequestBody EmailRequestDto emailRequestDto) {

      StatusResponseDto dtoStatus = userService.resendVerificationEmail(emailRequestDto);
      return new ResponseEntity<>(dtoStatus, HttpStatus.OK);
//     catch (InvalidEmailException e) {
//      return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (AlreadyVerifiedException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Email already verified!"), HttpStatus.BAD_REQUEST); HOTOVE
//    } finally {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR); ???????????????????? JAK ŘEŠIT?
//    }
  }

  @PostMapping("/reset-password")
  public ResponseEntity<? extends ResponseDto> resetPassword(
      @RequestBody EmailRequestDto emailDto) {

      StatusResponseDto dtoStatus = userService.resetPasswords(emailDto);
      return new ResponseEntity<>(dtoStatus, HttpStatus.OK);
//     catch (InvalidEmailException e) {
//      return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UnverifiedEmailException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Unverified email!"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UnknownErrorException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Something goes wrong"), HttpStatus.BAD_REQUEST); HOTOVE
//    }
  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<? extends ResponseDto> resetPassword(
      @RequestBody PasswordResetRequestDto passwordResetRequestDto, @PathVariable String token) {

      StatusResponseDto dtoStatus = userService.resetPassword(token, passwordResetRequestDto);
      return new ResponseEntity<>(dtoStatus, HttpStatus.OK);
//     catch (InvalidPasswordException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Invalid password!"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (InvalidTokenException e) {
//      return new ResponseEntity<>(new ErrorResponseDto("Invalid token!"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (TokenExpiredException e) {
//      return new ResponseEntity<>(new ErrorResponseDto("Expired token!"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (PasswordTooShortException e) {
//      return new ResponseEntity<>(
//          new ErrorResponseDto("Password must be at least 8 characters long"), HOTOVE
//          HttpStatus.BAD_REQUEST);
//    }
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
//    catch (InvalidIdException e) {
//
//      return new ResponseEntity<>(new UserErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST); HOTOVE
//
//    } catch (UserNotFoundException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("User not found"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (InvalidRequestException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("Invalid data"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UnknownErrorException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST); HOTOVE
//    }
//  }

  @PatchMapping("/users/{id}")
  public ResponseEntity<? extends ResponseDto> update(
      @PathVariable Long id, @RequestBody UserPatchRequestDto userPatchRequestDto) {

      UserResponseDto dtoResponse = userService.update(id, userPatchRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
//     catch (InvalidIdException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UserNotFoundException e) {
//      return new ResponseEntity<>(
//          new UserErrorResponseDto("User not found"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (InvalidRequestException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("Invalid data"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UnknownErrorException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST); HOTOVE
//    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable Long id) {

      EmptyResponseDto dtoResponse = userService.destroy(id);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
//     catch (InvalidIdException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UserNotFoundException e) {
//      return new ResponseEntity<>(
//          new UserErrorResponseDto("User not found"), HttpStatus.BAD_REQUEST); HOTOVE
//    } catch (UnknownErrorException e) {
//      return new ResponseEntity<>(new UserErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
//    }
  }
}
