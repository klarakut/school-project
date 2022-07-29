package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.exceptions.AlreadyVerifiedException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResendVerificationEmailTest {

  @Test
  void can_resend_the_verification_email() {
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
    UserService userService = new DatabaseUserService(mockedUserRepository, null, null, null);

    EmailRequestDto emailDto = new EmailRequestDto("alex@gmail.com");
    User user = Mockito.mock(User.class);

    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString()))
        .thenReturn(Optional.of(user));

    StatusResponseDto result = userService.resendVerificationEmail(emailDto);
    assertEquals("ok", result.status);
  }

  @Test
  void resend_to_an_already_verified_user_throw_an_exception() {
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
    UserService userService = new DatabaseUserService(mockedUserRepository, null, null, null);

    EmailRequestDto emailDto = new EmailRequestDto("alex@gmail.com");
    User user = Mockito.mock(User.class);
    LocalDateTime time = LocalDateTime.now();

    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString()))
        .thenReturn(Optional.of(user));
    Mockito.when(user.getVerifiedAt()).thenReturn(time);

    assertThrows(
        AlreadyVerifiedException.class, () -> userService.resendVerificationEmail(emailDto));
  }

  @Test
  void resend_with_an_invalid_email_throw_an_exception() {
    UserRepository mockedUserRepo = Mockito.mock(UserRepository.class);
    UserService userService = new DatabaseUserService(mockedUserRepo, null, null, null);

    EmailRequestDto emailDto = new EmailRequestDto("alexgmail.com");

    assertThrows(InvalidEmailException.class, () -> userService.resendVerificationEmail(emailDto));
  }

  @Test
  void resend_to_an_non_existent_user_fails_silently() {
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
    UserService userService = new DatabaseUserService(mockedUserRepository, null, null, null);
    EmailRequestDto emailDto = new EmailRequestDto("alex@gmail.com");
    User user = Mockito.mock(User.class);

    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString()))
        .thenReturn(Optional.empty());

    StatusResponseDto result = userService.resendVerificationEmail(emailDto);
    assertEquals("ok", result.status);
  }
}
