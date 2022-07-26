package com.gfa.users.services;

import com.gfa.common.dtos.LoginRequestDto;
import com.gfa.users.dtos.CreateUserRequestDto;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.InvalidLoginCredentialsExpcetion;
import com.gfa.users.exceptions.PasswordMissingException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginServiceImplTest {

  UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
  LoginService loginService = new LoginServiceImpl(mockedUserRepository,null,null);

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
    //LoginRequestDto mockedDto = Mockito.mock(LoginRequestDto.class);
    // User user = new User ()

    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
    assertThrows(InvalidLoginCredentialsExpcetion.class,() -> loginService.login(dto));
  }

  @Test
    void login_method_throws_exception_if_the_password_is_wrong() {
    LoginRequestDto dto = new LoginRequestDto("alex@gmail.com","abc","1234");
    CreateUserRequestDto request = new CreateUserRequestDto("pepa","pepa@gmail.com","1234");
    User user = new User(request,600L,null);
    //User user = Mockito.mock(User.class);
    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    assertThrows(InvalidLoginCredentialsExpcetion.class,() -> loginService.login(dto));
  }

  @Test
    void can_login() {
    /*LoginRequestDto dto = new LoginRequestDto("alex@gmail.com","abc","1234");

    Mockito.when(mockedUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
    assertThrows(InvalidLoginCredentialsException.class,() -> loginService.login(dto));*/
  }
}