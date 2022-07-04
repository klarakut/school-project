package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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

  // private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final EmailValidator emailValidator;
  private final UserRepository userRepository;

  // private final EmailUtils emailUtils;

  public UserServiceImpl(EmailValidator emailValidator, UserRepository userRepository) {
    this.emailValidator = emailValidator;
    this.userRepository = userRepository;
  }

  public ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto) {

    if (dto.username.isEmpty()) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Username is required"), HttpStatus.BAD_REQUEST);
    }
    if (dto.password.isEmpty()) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Password is required"), HttpStatus.BAD_REQUEST);
    }
    if (dto.email.isEmpty()) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Email is required"), HttpStatus.BAD_REQUEST);
    }

    boolean usernameExist = userRepository.findByUsername(dto.username).isPresent();
    if (usernameExist) {
      ErrorResponseDto error = new ErrorResponseDto("Username is already taken");
      return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /*boolean userExist = userRepository.findByEmail(dto.email).isPresent();
    if(userExist){
        // throw new IllegalStateException("email already taken ");
        ErrorResponseDto error = new ErrorResponseDto("Email is already taken");
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }*/

    if (dto.username.length() < 4) {
      ErrorResponseDto error = new ErrorResponseDto("Username must be at least 4 characters long");
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    if (dto.password.length() < 8) {
      ErrorResponseDto error = new ErrorResponseDto("Password must be at least 8 characters long");
      return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    boolean isValidEmail = emailValidator.isValid(dto.email);
    if (!isValidEmail) {
      // throw new IllegalStateException("email not valid");
      ErrorResponseDto error = new ErrorResponseDto("Invalid email");
      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    // user.setPassword(encodedPassword);
    /*
    User user = new User(dto);
    userRepository.save(user);
    UserResponseDto userResponseDto = new UserResponseDto(user);
    return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.CREATED);

     */
    return null;
  }

  @Override
  public ResponseEntity<? extends ResponseDto> resetPasswords(EmailRequestDto emailDto) {
    boolean isValidEmail = emailValidator.isValid(emailDto.email);

    User user = userRepository.findByEmail(emailDto.email);
    if (user == null) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid email!"), HttpStatus.OK);
    }
    if (!isValidEmail || emailDto.email.isEmpty()) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Invalid email!"), HttpStatus.resolve(BAD_REQUEST));
    }
    if (user.getVerifiedAt() == null) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Unverified email"), HttpStatus.resolve(BAD_REQUEST));
    }
    if (user.getEmail().equals(emailDto.email) && user.getVerifiedAt() != null) {
      /*
      emailUtils.sendHtmlEmail(user.getEmail(), "support@demo", "Password Reset Request", "To reset your password, click the link below:\n"
            + http://localhost:3036/email/reset-password
            + "/reset?token="
            + user.getForgottenPasswordToken());
      return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);

      */
    }

    return new ResponseEntity<>(
        new ErrorResponseDto("Something goes wrong"), HttpStatus.BAD_REQUEST);
  }

  @Override
  public ResponseEntity<? extends ResponseDto> resetPasswordViaToken(
      String token, PasswordResetRequestDto resetPassword) {
    User user = userRepository.findByForgottenPasswordToken(token);
    Date currentDate = new Date(System.currentTimeMillis());

    if (resetPassword.password.isEmpty()) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Password is required"), HttpStatus.BAD_REQUEST);
    }
    if (!user.getForgottenPasswordToken().equals(token)) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid token"), HttpStatus.BAD_REQUEST);
    }
    if (currentDate.after(user.getVerificationTokenExpiresAt())) {
      return new ResponseEntity<>(new ErrorResponseDto("Expired token"), HttpStatus.BAD_REQUEST);
    }
    if (resetPassword.password.length() <= 8) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Password must be at least 8 characters long"),
          HttpStatus.BAD_REQUEST);
      /*
      emailUtils.sendHtmlEmail(user.getEmail(), "support@demo", "Password Reset Request", "To reset your password, click the link below:\n"
              + http://localhost:3036/email/reset-password
      + "/reset?token="
              + user.getForgottenPasswordToken());
      return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);

       */
    }
    if (user.getForgottenPasswordToken().equals(token)
        && currentDate.before(user.getForgottenPasswordTokenExpiresAt())
        && resetPassword.password.equals(
            user.getPassword())) {
      user.setPassword(resetPassword.password);
      user.setForgottenPasswordToken(null);
      userRepository.save(user);
      return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);
    }
    if (user.getForgottenPasswordToken().equals(token)
        && currentDate.before(user.getForgottenPasswordTokenExpiresAt())) {
      user.setPassword(resetPassword.password);
      user.setForgottenPasswordToken(null);
      userRepository.save(user);
      return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorResponseDto("Something goes wrong"), HttpStatus.BAD_REQUEST);
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
