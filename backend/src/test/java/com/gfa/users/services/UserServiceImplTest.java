package com.gfa.users.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
<<<<<<< HEAD
import com.gfa.users.Exceptions.InvalidEmailException;
import com.gfa.users.Exceptions.InvalidPasswordException;
import com.gfa.users.Exceptions.InvalidTokenException;
import com.gfa.users.Exceptions.UnverifiedEmailExeption;
=======
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.PasswordTooShortException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.exceptions.UserNotFoundException;
>>>>>>> develop
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserServiceImplTest {

  @Test
  void sending_empty_email_exception() {
    UserService userService = new UserServiceImpl(null, null);
    assertThrows(
        InvalidEmailException.class,
        () -> {
          userService.resetPasswords(new EmailRequestDto(""));
        });
  }

  @Test
  void user_not_found_in_repository_exception() {

<<<<<<< HEAD
/*
=======
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByEmail(Mockito.anyString()))
        .thenThrow(new UserNotFoundException());

    UserService userService = new UserServiceImpl(userRepository, null);
    assertThrows(
        UserNotFoundException.class,
        () -> userService.resetPasswords(new EmailRequestDto("milarda@milarda.sk")));
  }
>>>>>>> develop

  @Test
  void user_without_verification_exception() {
    User user = Mockito.mock(User.class);
    Mockito.when(user.getVerifiedAt()).thenReturn(null);

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null);

    assertThrows(
        UnverifiedEmailException.class,
        () -> userService.resetPasswords(new EmailRequestDto("gregorovic@mail.com")));
  }

  @Test
  void sending_empty_password_exception() {

<<<<<<< HEAD
  @Test
  void sending_empty_password_exception(){
    assertThrows(InvalidPasswordException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto(""));});
  }
  @Test
  void not_match_forgottenPasswordToken_exception(){
    User user = new User();
    user.setForgottenPasswordToken("23");
    userRepository.save(user);
    assertThrows(InvalidTokenException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto("DVAVVVAAA"));});
  }

  @Test
  void request_reset_password_after_expiration_token_exception(){
    User user = new User();
    user.setForgottenPasswordTokenExpiresAt(new Date(System.currentTimeMillis() - 20000));
    user.setForgottenPasswordToken("22");
    userRepository.save(user);
    assertThrows(InvalidTokenException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto("DVAVVVAAA"));});
  }

  @Test
  void request_reset_password_is_less_than_8_characters_exception(){
    User user = new User();
    user.setForgottenPasswordTokenExpiresAt(new Date(System.currentTimeMillis() + 3000));
    user.setForgottenPasswordToken("22");
    userRepository.save(user);
    assertThrows(InvalidPasswordException.class, () => {userServiceImpl.resetPassword("22", new PasswordResetRequestDto("Less"));});
=======
    User user = Mockito.mock(User.class);
    Mockito.when(user.getForgottenPasswordTokenExpiresAt())
        .thenReturn(LocalDateTime.now().plusSeconds(10));

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByForgottenPasswordToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null);
    assertThrows(
        InvalidPasswordException.class,
        () -> userService.resetPassword("22", new PasswordResetRequestDto("")));
  }

  @Test
  void reset_with_an_incorrect_token_throws_an_exception() {

    User user = Mockito.mock(User.class);
    Mockito.when(user.getForgottenPasswordTokenExpiresAt())
        .thenReturn(LocalDateTime.now().plusSeconds(10));

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByForgottenPasswordToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null);
    assertThrows(
        InvalidTokenException.class,
        () -> userService.resetPassword("22", new PasswordResetRequestDto("DVAVVVAAA")));
  }

  @Test
  void reset_with_an_expired_token_throws_an_exception() {

    User user = Mockito.mock(User.class);
    Mockito.when(user.getForgottenPasswordTokenExpiresAt())
        .thenReturn(LocalDateTime.now().minusSeconds(10));

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByForgottenPasswordToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null);
    assertThrows(
        TokenExpiredException.class,
        () -> userService.resetPassword("22", new PasswordResetRequestDto("DVAVVVAAA")));
>>>>>>> develop
  }

  @Test
  void reset_to_a_new_password_that_is_too_short_throws_an_exception() {

<<<<<<< HEAD
 */

=======
    User user = Mockito.mock(User.class);
    Mockito.when(user.getForgottenPasswordTokenExpiresAt())
        .thenReturn(LocalDateTime.now().plusSeconds(10));
>>>>>>> develop

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByForgottenPasswordToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null);
    assertThrows(
        PasswordTooShortException.class,
        () -> userService.resetPassword("22", new PasswordResetRequestDto("1")));
  }

  @Test
  void can_reset_to_a_new_valid_password() {

    User user = Mockito.mock(User.class);
    Mockito.when(user.getForgottenPasswordTokenExpiresAt())
        .thenReturn(LocalDateTime.now().plusSeconds(10));
    Mockito.when(user.getForgottenPasswordToken()).thenReturn("22");

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByForgottenPasswordToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null);
    assertDoesNotThrow(
        () -> userService.resetPassword("22", new PasswordResetRequestDto("V4l1d n3w P4$$w0rd")));

    StatusResponseDto dto =
        userService.resetPassword("22", new PasswordResetRequestDto("V4l1d n3w P4$$w0rd"));
    assertEquals("ok", dto.status);
  }
}
