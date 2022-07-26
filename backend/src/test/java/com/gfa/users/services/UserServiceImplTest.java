package com.gfa.users.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.util.AssertionErrors.assertNull;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserPatchRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PasswordTooShortException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserServiceImplTest {

  @Test
  void sending_empty_email_exception() {
    UserService userService = new UserServiceImpl(null, null, null, null);
    assertThrows(
        InvalidEmailException.class,
        () -> {
          userService.resetPasswords(new EmailRequestDto(""));
        });
  }

  @Test
  void user_not_found_in_repository_exception() {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByEmail(Mockito.anyString()))
        .thenThrow(new UserNotFoundException());

    UserService userService = new UserServiceImpl(userRepository, null, null, null);
    assertThrows(
        UserNotFoundException.class,
        () -> userService.resetPasswords(new EmailRequestDto("milarda@milarda.sk")));
  }

  @Test
  void user_without_verification_exception() {
    User user = Mockito.mock(User.class);
    Mockito.when(user.getVerifiedAt()).thenReturn(null);

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null, null, null);

    assertThrows(
        UnverifiedEmailException.class,
        () -> userService.resetPasswords(new EmailRequestDto("gregorovic@mail.com")));
  }

  @Test
  void sending_empty_password_exception() {

    User user = Mockito.mock(User.class);
    Mockito.when(user.getForgottenPasswordTokenExpiresAt())
        .thenReturn(LocalDateTime.now().plusSeconds(10));

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByForgottenPasswordToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null, null, null);
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

    UserService userService = new UserServiceImpl(userRepository, null, null, null);
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

    UserService userService = new UserServiceImpl(userRepository, null, null, null);
    assertThrows(
        TokenExpiredException.class,
        () -> userService.resetPassword("22", new PasswordResetRequestDto("DVAVVVAAA")));
  }

  @Test
  void reset_to_a_new_password_that_is_too_short_throws_an_exception() {

    User user = Mockito.mock(User.class);
    Mockito.when(user.getForgottenPasswordTokenExpiresAt())
        .thenReturn(LocalDateTime.now().plusSeconds(10));

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.when(userRepository.findByForgottenPasswordToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    UserService userService = new UserServiceImpl(userRepository, null, null, null);
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

    UserService userService = new UserServiceImpl(userRepository, null, null, null);
    assertDoesNotThrow(
        () -> userService.resetPassword("22", new PasswordResetRequestDto("V4l1d n3w P4$$w0rd")));

    StatusResponseDto dto =
        userService.resetPassword("22", new PasswordResetRequestDto("V4l1d n3w P4$$w0rd"));
    assertEquals("ok", dto.status);
  }

  @Test
  void delete_user_with_negative_id() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    assertThrows(InvalidIdException.class, () -> userService.destroy(-1L));
  }

  @Test
  void delete_user_with_not_exist_id() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    assertThrows(UserNotFoundException.class, () -> userService.destroy(1L));
  }

  @Test
  void update_negative_id_throws_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto mockedDto = Mockito.mock(UserPatchRequestDto.class);

    assertThrows(InvalidIdException.class, () -> userService.update(-1L, mockedDto));
  }

  @Test
  void update_user_with_not_exist_id() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto dto = new UserPatchRequestDto("petr@seznam.cz", "petr", "123456789");
    assertThrows(UserNotFoundException.class, () -> userService.update(1L, dto));
  }

  @Test
  void update_username_is_empty_throws_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto dto = new UserPatchRequestDto("petr@seznam.cz", "",  "123456789");
    assertThrows(InvalidRequestException.class, () -> userService.update(1L, dto));
  }

  @Test
  void update_username_is_null_throws_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto dto = new UserPatchRequestDto("petr@seznam.cz", null,  "123456789");
    assertThrows(InvalidRequestException.class, () -> userService.update(1L, dto));
  }

  @Test
  void update_email_is_empty_throws_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto dto = new UserPatchRequestDto("", "petr",  "123456789");
    assertThrows(InvalidRequestException.class, () -> userService.update(1L, dto));
  }

  @Test
  void update_email_is_null_throws_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto dto = new UserPatchRequestDto(null, "petr",  "123456789");
    assertThrows(InvalidRequestException.class, () -> userService.update(1L, dto));
  }

  @Test
  void update_password_is_empty_throws_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto dto = new UserPatchRequestDto("petr@seznam.cz", "petr",  "");
    assertThrows(InvalidRequestException.class, () -> userService.update(1L, dto));
  }

  @Test
  void update_password_is_null_throws_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);

    UserPatchRequestDto dto = new UserPatchRequestDto("petr@seznam.cz", "petr",  null);
    assertThrows(InvalidRequestException.class, () -> userService.update(1L, dto));
  }

  @Test
  void update_user_email_username_password_successfully() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(mockedUserRepo, null, null, null);
    User user = new User(1L,"peter","peter@seznam.cz", "123456789", null, null,
            null, null, null, null);
    UserPatchRequestDto dto = new UserPatchRequestDto("peeeetr@seznam.cz", "peeeetr",  "987654321");

    Mockito.when(mockedUserRepo.findById(anyLong())).thenReturn(Optional.of(user));
    Mockito.when(mockedUserRepo.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
    Mockito.when(mockedUserRepo.findByEmail(anyString())).thenReturn(Optional.of(user));

    UserResponseDto result = userService.update(1L, dto);

    assertEquals(dto.username, result.username);
    assertEquals(dto.email, result.email);
    assertEquals(dto.password, result.password);
    assertNull(result.verifiedAt);

  }
}
