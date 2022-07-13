package com.gfa.users.controllers;

import com.gfa.common.dtos.*;
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

  @Autowired private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<? extends ResponseDto> store(@RequestBody CreateUserRequestDto dto){
        return userService.store(dto);
    }

    @PostMapping("/email/verify/resend")
    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(@RequestBody EmailRequestDto emailRequestDto){
        try{
            StatusResponseDto dtoStatus = userService.resendVerificationEmail(emailRequestDto);
            return new ResponseEntity<>(dtoStatus, HttpStatus.OK);
        }
        catch (InvalidEmailException e){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid email"),HttpStatus.BAD_REQUEST);
        }
        catch (AlreadyVerifiedException e){
            return new ResponseEntity<>(new ErrorResponseDto("Email already verified!"),HttpStatus.BAD_REQUEST);
        }
        finally {
            return new ResponseEntity<>(new ErrorResponseDto("Unknown error"),HttpStatus.INTERNAL_SERVER_ERROR);
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
        } catch (UnverifiedEmailExeption e) {
            return new ResponseEntity<>(new ErrorResponseDto("Unverified email!"), HttpStatus.BAD_REQUEST);
        } catch (InvalidRequestException e) {
            return new ResponseEntity<>(new ErrorResponseDto("Something goes wrong"), HttpStatus.BAD_REQUEST);
        }
      }

      @PostMapping("/reset-password/{token}")
      public ResponseEntity<? extends ResponseDto> resetPassword(
          @RequestBody PasswordResetRequestDto passwordResetRequestDto, @PathVariable String token) {
        try {
          StatusResponseDto dtoStatus = userService.resetPassword(token, passwordResetRequestDto);
            return new ResponseEntity<>(dtoStatus, HttpStatus.OK);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(new ErrorResponseDto("Invalid password!"), HttpStatus.BAD_REQUEST);
        } catch (InvalidTokenException e) {
            return new ResponseEntity<>(new ErrorResponseDto("Invalid token!"), HttpStatus.BAD_REQUEST);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(new ErrorResponseDto("Expired token!"), HttpStatus.BAD_REQUEST);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(new ErrorResponseDto("Password must be at leat 8 characters long"), HttpStatus.BAD_REQUEST);
        }
      }
}
