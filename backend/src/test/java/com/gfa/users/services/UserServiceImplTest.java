package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.PasswordResetRequestDto;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

  @Test
  void resetPasswords() {
    // AAA
    // Arrange
    UserRepository fakeRepo = Mockito.mock(UserRepository.class);
    UserService fakeService = Mockito.mock(UserService.class);
    EmailValidator fakeEmailValidator = Mockito.mock(EmailValidator.class);

    Date date = new Date(1000L);
    EmailRequestDto emailDto = new EmailRequestDto("gregorgregorovic@gmail.com");

    User fake1 =
        new User(
            3L,
            "gregor",
            "gregorgregorovic@gmail.com",
            "gregr",
            date,
            "Gregor",
            date,
            "Gregor",
            date,
            date);

    Mockito.when(fakeRepo.findByEmail(Mockito.anyString())).thenReturn(fake1); // save to the database
    Mockito.when(fakeService.resetPasswords(emailDto))
        .thenReturn(new ResponseEntity<>(HttpStatus.OK));

    UserService userService = new UserServiceImpl(fakeEmailValidator, fakeRepo);

    // Act
    ResponseEntity result = userService.resetPasswords(emailDto);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  void resetPasswordViaToken() {
    // AAA
    // Arrange
    UserRepository fakeRepo = Mockito.mock(UserRepository.class);
    UserService fakeService = Mockito.mock(UserService.class);
    EmailValidator fakeEmailValidator = Mockito.mock(EmailValidator.class);

    Date date = new Date(System.currentTimeMillis() + 200000);
    PasswordResetRequestDto resetPasswordViaToken = new PasswordResetRequestDto("mamamiaMIA");
    String token = new String("cd");

    User fake1 =
            new User(
                    3L,
                    "gregor",
                    "gregorgregorovic@gmail.com",
                    "gregrovicovic",
                    date,
                    "Gregor",
                    date,
                    "cd",
                    date,
                    date);

    Mockito.when(fakeRepo.findByForgottenPasswordToken(Mockito.anyString())).thenReturn(fake1); // save to the database
    Mockito.when(fakeService.resetPasswordViaToken(token,resetPasswordViaToken)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

    UserService userService = new UserServiceImpl(fakeEmailValidator,fakeRepo);

    // Act
    ResponseEntity result = userService.resetPasswordViaToken(token,resetPasswordViaToken);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
  }
}
