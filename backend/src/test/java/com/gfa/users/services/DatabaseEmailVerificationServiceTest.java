package com.gfa.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.exceptions.EmailAlreadyVerifiedException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DatabaseEmailVerificationServiceTest {

  private final DatabaseEmailVerificationService service;

  public DatabaseEmailVerificationServiceTest() {
    UserRepository repository = Mockito.mock(UserRepository.class);
    service = new DatabaseEmailVerificationService(repository);
  }

  @Test
  void verify_with_no_or_empty_token_throws_an_exception() {
    assertThrows(InvalidTokenException.class, () -> service.verify(null));
    assertThrows(InvalidTokenException.class, () -> service.verify(""));
  }

  @Test
  void verify_with_invalid_token_throws_an_exception() {
    assertThrows(InvalidTokenException.class, () -> service.verify("non-existent token"));
  }

  @Test
  void verify_with_valid_token_returns_response_status_dto() {

    User user = new User();
    user.setVerificationToken("token");
    user.setVerificationTokenExpiresAt(LocalDateTime.now().plusSeconds(10));

    UserRepository repository = Mockito.mock(UserRepository.class);
    Mockito.when(repository.findByVerificationToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));
    DatabaseEmailVerificationService service = new DatabaseEmailVerificationService(repository);

    StatusResponseDto dto = service.verify("token");
    assertNotNull(dto);
    assertEquals("ok", dto.status);
  }

  @Test
  void verify_with_an_expired_token_throws_an_exception() {

    User user = new User();
    user.setVerificationToken("token");
    user.setVerificationTokenExpiresAt(LocalDateTime.now().minusSeconds(10));

    UserRepository repository = Mockito.mock(UserRepository.class);
    Mockito.when(repository.findByVerificationToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));
    DatabaseEmailVerificationService service = new DatabaseEmailVerificationService(repository);

    assertThrows(TokenExpiredException.class, () -> service.verify("token"));
  }

  @Test
  void verify_with_an_already_verified_email_throws_an_exception() {

    User user = new User();
    user.setVerificationToken("token");
    user.setVerified();

    UserRepository repository = Mockito.mock(UserRepository.class);
    Mockito.when(repository.findByVerificationToken(Mockito.anyString()))
        .thenReturn(Optional.of(user));
    DatabaseEmailVerificationService service = new DatabaseEmailVerificationService(repository);

    assertThrows(EmailAlreadyVerifiedException.class, () -> service.verify("token"));
  }

  @Test
  void resend_with_request_an_existing_email_returns_status_response_dto() {

    User user = new User();
    user.setEmail("user@example.com");

    UserRepository repository = Mockito.mock(UserRepository.class);
    Mockito.when(repository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
    DatabaseEmailVerificationService service = new DatabaseEmailVerificationService(repository);

    StatusResponseDto dto = service.resend(new EmailRequestDto("user@example.com"));
    assertNotNull(dto);
    assertEquals("ok", dto.status);
  }

  @Test
  void resend_with_request_non_existing_email_throws_an_exception() {
    assertThrows(
        UserNotFoundException.class, () -> service.resend(new EmailRequestDto("user@example.com")));
  }

  @Test
  void resend_with_no_body_throws_an_exception() {
    assertThrows(RequestBodyMissingException.class, () -> service.resend(null));
  }
}
