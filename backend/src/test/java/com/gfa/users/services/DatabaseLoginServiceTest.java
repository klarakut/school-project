package com.gfa.users.services;

import com.gfa.common.dtos.LoginRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.InvalidLoginCredentialsException;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.PasswordMissingException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseLoginServiceTest {

  UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
  AuthenticationManager mockedAuthManager = Mockito.mock(AuthenticationManager.class);
  LoginService loginService = new DatabaseLoginService(mockedUserRepository,mockedAuthManager,null);

  @Test
    void login_method_throws_exception_if_empty() {
    assertThrows(RequestBodyMissingException.class,() -> loginService.login(null));
  }

  @Test
    void login_method_throws_exception_if_email_is_empty() {
    LoginRequestDto dto = new LoginRequestDto("","1234","1234");

    assertThrows(EmailMissingException.class,() -> loginService.login(dto));
  }

  @Test
    void login_method_throws_exception_if_password_is_empty() {
    LoginRequestDto dto = new LoginRequestDto("alex@gmail.com","","1234");

    assertThrows(PasswordMissingException.class,() -> loginService.login(dto));
  }

  @Test
    void login_method_throws_exception_if_non_existent_user_is_trying_login() {
    LoginRequestDto dto = new LoginRequestDto("alex@gmail.com","abc","1234");

    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
    assertThrows(InvalidLoginCredentialsException.class,() -> loginService.login(dto));
  }

  @Test
    void login_method_throws_exception_if_the_password_is_wrong() {
    LoginRequestDto dto = new LoginRequestDto("alex@gmail.com","abc","1234");
    User user = Mockito.mock(User.class);
    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    Mockito.when(mockedAuthManager.authenticate(Mockito.any())).thenThrow(InvalidPasswordException.class);

    assertThrows(InvalidLoginCredentialsException.class,() -> loginService.login(dto));
  }

  @Test
    void user_can_login() {
    LoginRequestDto dto = new LoginRequestDto("alex@gmail.com","abc","1234");
    User user = Mockito.mock(User.class);
    Authentication authentication = Mockito.mock(Authentication.class);
    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    Mockito.when(mockedAuthManager.authenticate(Mockito.any())).thenReturn(authentication);

    StatusResponseDto result = loginService.login(dto);
    assertEquals("OK",result.status);
  }
}