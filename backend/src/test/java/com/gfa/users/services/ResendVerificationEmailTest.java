package com.gfa.users.services;

import com.gfa.users.dtos.EmailRequestDto;
import com.gfa.users.models.User;
import com.gfa.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

class ResendVerificationEmailTest {

    @Test
    void resend_Verification_Email_OK() {
        // AAA
        // Arrange
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService mockedUserService = Mockito.mock(UserService.class);
        EmailValidator mockedEmailValidator = Mockito.mock(EmailValidator.class);

        EmailRequestDto emailDto = new EmailRequestDto("alex@gmail.com");
        User user1 = new User(emailDto.email);

        Mockito.when(mockedEmailValidator.isValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedUserRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user1));
        Mockito.when(mockedUserService.resendVerificationEmail(emailDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        UserService userService = new UserServiceImpl(mockedUserRepository,mockedEmailValidator);

        // Act
        ResponseEntity result = userService.resendVerificationEmail(emailDto);

        // Assert
        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void resend_verification_email_to_already_verified_user() {
        // AAA
        // Arrange
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService mockedUserService = Mockito.mock(UserService.class);
        EmailValidator mockedEmailValidator = Mockito.mock(EmailValidator.class);

        Date date = new Date(1000L);
        EmailRequestDto emailDto = new EmailRequestDto("alex@gmail.com");
        User user = new User(emailDto.email,date);

        Mockito.when(mockedEmailValidator.isValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedUserRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(mockedUserService.resendVerificationEmail(emailDto)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        UserService userService = new UserServiceImpl(mockedUserRepository,mockedEmailValidator);

        // Act
        ResponseEntity result = userService.resendVerificationEmail(emailDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
    }

    @Test
    void resend_verification_email_invalid_email() {
        // AAA
        // Arrange
        UserRepository fakeRepo = Mockito.mock(UserRepository.class);
        UserService fakeService = Mockito.mock(UserService.class);
        EmailValidator mockedEmailValidator = Mockito.mock(EmailValidator.class);

        Date date = new Date(1000L);
        EmailRequestDto emailDto = new EmailRequestDto("alexgmail.com");
        User user = new User(emailDto.email);

        Mockito.when(mockedEmailValidator.isValid(Mockito.anyString())).thenReturn(false);
        Mockito.when(fakeService.resendVerificationEmail(emailDto)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        UserService userService = new UserServiceImpl(fakeRepo,mockedEmailValidator);

        // Act
        ResponseEntity result = userService.resendVerificationEmail(emailDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
    }

    @Test
    void resend_verification_email_non_existent_user() {
            //supposed to get OK status for security reasons

        // AAA
        // Arrange
        UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
        UserService mockedUserService = Mockito.mock(UserService.class);
        EmailValidator mockedEmailValidator = Mockito.mock(EmailValidator.class);

        EmailRequestDto emailDto = new EmailRequestDto("alex@gmail.com");
        Mockito.when(mockedEmailValidator.isValid(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedUserService.resendVerificationEmail(emailDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        UserService userService = new UserServiceImpl(mockedUserRepository,mockedEmailValidator);

        // Act
        ResponseEntity result = userService.resendVerificationEmail(emailDto);

        // Assert
        assertEquals(HttpStatus.OK,result.getStatusCode());
    }
}
