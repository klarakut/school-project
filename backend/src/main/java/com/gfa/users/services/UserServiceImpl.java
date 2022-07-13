package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static sun.security.timestamp.TSResponse.BAD_REQUEST;

@Service
public class UserServiceImpl implements UserService {

 private final EmailValidator emailValidator;
  private final UserRepository userRepository;
  private final Long tokenExpiration;

  public UserServiceImpl(
      EmailValidator emailValidator,
      UserRepository userRepository,
      @Value("${token_expiration}") Long tokenExpiration) {
    this.emailValidator = emailValidator;
    this.userRepository = userRepository;
    this.tokenExpiration = tokenExpiration;
  }


  @Override
  public StatusResponseDto resetPasswords(EmailRequestDto emailDto) {
    boolean isValidEmail = emailValidator.isValid(emailDto.email);

    if (!isValidEmail || emailDto.email.isEmpty()) {
      throw new InvalidEmailException();
    }
    User user = userRepository.findByEmail(emailDto.email);
    if (user == null) {
      return new StatusResponseDto("ok");
    }
    if (user.getVerifiedAt() == null) {
      throw new UnverifiedEmailExeption();
      }
    }

 

  public ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto) {

    if (dto.username.isEmpty()) {
      throw new RuntimeException();
    }
    if (dto.password.isEmpty()) {
      throw new RuntimeException();
    }
    if (dto.email.isEmpty()) {
      throw new RuntimeException();
    }

    boolean usernameExist = userRepository.findByUsername(dto.username).isPresent();
    if (usernameExist) {
      ErrorResponseDto error = new ErrorResponseDto("Username is already taken");
      throw new RuntimeException();
    }

    if (dto.username.length() < 4) {
      ErrorResponseDto error = new ErrorResponseDto("Username must be at least 4 characters long");
      throw new RuntimeException();
    }

    if (dto.password.length() < 8) {
      ErrorResponseDto error = new ErrorResponseDto("Password must be at least 8 characters long");
      throw new RuntimeException();
    }

    boolean isValidEmail = emailValidator.isValid(dto.email);
    if (!isValidEmail) {
      ErrorResponseDto error = new ErrorResponseDto("Invalid email");
      throw new RuntimeException();

    }
    if (user.getEmail().equals(emailDto.email) && user.getVerifiedAt() != null) {
      /*
       emailUtils.sendHtmlEmail(user.getEmail(), "support@demo", "Password Reset Request", "To reset your password, click the link below:\n"
             + "http://localhost:3036/email/reset-password"
             + "/reset?token="
             + user.getForgottenPasswordToken());
      */
      return new StatusResponseDto("ok");
    }
    return new InvalidRequestException();
  }


  public StatusResponseDto resetPassword(String token, PasswordResetRequestDto resetPassword) {
    User user = userRepository.findByForgottenPasswordToken(token);
    Date currentDate = new Date(System.currentTimeMillis());

    if (resetPassword.password.isEmpty()) {
      throw new InvalidPasswordExeption();
    }
    if (!user.getForgottenPasswordToken().equals(token)) {
      throw new InvalidTokenExeption();
    }
    if (currentDate.after(user.getForgottenPasswordTokenExpiresAt())) {
      throw new InvalidTokenExeption();
    }
    if (resetPassword.password.length() <= 8) {
      throw new InvalidPasswordExeption();
      /*
         emailUtils.sendHtmlEmail(user.getEmail(), "support@demo", "Password Reset Request", "To reset your password, click the link below:\n"
                 + "http://localhost:3036/email/reset-password"
         + "/reset?token="
                 + user.getForgottenPasswordToken());

      */
    }
    if (user.getForgottenPasswordToken().equals(token)
        && currentDate.before(user.getForgottenPasswordTokenExpiresAt())) {
      user.setPassword(resetPassword.password);
      user.setForgottenPasswordToken(null);
      userRepository.save(user);
      return new StatusResponseDto("ok");
    }
    return new InvalidRequestException();

  }

  @Override
  public ResponseEntity<ResponseDto> index() {
    return null;
  }

  @Override
  public ResponseEntity<ResponseDto> show(Long id) {
    return null;
  }


  /*@Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      return userRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }*/

}

