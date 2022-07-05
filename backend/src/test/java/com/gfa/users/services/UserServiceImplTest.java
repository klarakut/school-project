package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.PasswordResetRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {





  private UserServiceImpl userServiceImpl;
  private EmailValidator emailValidator;
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    this.userServiceImpl = new UserServiceImpl(emailValidator,userRepository);
    }

    @Test
    void  sending_empty_email_exception (){
      assertThrows(InvalidEmailException.class, () => {userServiceImpl.resetPasswords(new EmailRequestDto(""));});
      }

        @Test
  void user_not_found_in_repository_exception(){
    assertThrows(new StatusResponseDto("ok"), () => {userServiceImpl.resetPasswords(new EmailRequestDto("milarda@milarda.sk")); })
  }
  @Test
  void user_without_verification_exception(){
    User user = new User();
    user.setEmail("gregorovic@mail.com");
    userRepository.save(user);
    assertThrows(UnverifiedEmailExeption.class, () => {userServiceImpl.resetPasswords(new EmailRequestDto("gregorovic@mail.com"));})
  }

  @Test
  void user_with_verification_exception(){
    User user = new User();
    user.setEmail("gregorovic@mail.com");
    userRepository.save(user);
    assertThrows(new StatusResponseDto("ok"), () => {userServiceImpl.resetPasswords(new EmailRequestDto("gregorovic@mail.com"));})
  }

  @Test
  void sending_empty_password_exception(){
    assertThrows(InvalidEmailException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto(""));});
  }
  @Test
  void not_match_forgottenPasswordToken_exception(){
    User user = new User();
    user.setForgottenPasswordToken("23");
    userRepository.save(user);
    assertThrows(InvalidEmailException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto("DVAVVVAAA"));});
  }

  @Test
  void request_reset_password_after_expiration_token_exception(){
    User user = new User();
    user.setForgottenPasswordTokenExpiresAt(new Date(System.currentTimeMillis() - 20000));
    user.setForgottenPasswordToken("22");
    userRepository.save(user);
    assertThrows(InvalidEmailException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto("DVAVVVAAA"));});
  }

  @Test
  void request_reset_password_is_less_than_8_characters_exception(){
    User user = new User();
    user.setForgottenPasswordTokenExpiresAt(new Date(System.currentTimeMillis() + 3000));
    user.setForgottenPasswordToken("22");
    userRepository.save(user);
    assertThrows(InvalidEmailException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto("Less"));});
  }

  @Test
  void request_reset_password_is_less_than_8_characters_exception(){
    User user = new User();
    user.setForgottenPasswordTokenExpiresAt(new Date(System.currentTimeMillis() + 3000));
    user.setForgottenPasswordToken("22");
    userRepository.save(user);
    assertThrows(new StatusResponseDto("ok"), () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto("Lessssssssss"));});
  }







}
