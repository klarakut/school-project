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

@Service
public class UserServiceImpl implements UserService {

  // private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final EmailValidator emailValidator;
  private final UserRepository userRepository;

  private final EmailServiceImpl emailServiceImpl;

  public UserServiceImpl(
      EmailValidator emailValidator,
      UserRepository userRepository,
      EmailServiceImpl emailServiceImpl) {
    this.emailValidator = emailValidator;
    this.userRepository = userRepository;
    this.emailServiceImpl = emailServiceImpl;
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

    User user = new User(dto);
    userRepository.save(user);
    UserResponseDto userResponseDto = new UserResponseDto(user);
    return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<? extends ResponseDto> resetPasswords(EmailRequestDto emailDto) {
    if (!userRepository.existsByEmail(emailDto.email)) { // validate user by receive email
      return new ResponseEntity<>(new ErrorResponseDto("invalid email!"), HttpStatus.BAD_REQUEST);
    } else {
      User user = userRepository.findByEmail(emailDto.email); // find user by email
      user.setForgottenPasswordToken(UUID.randomUUID().toString()); // create a Token
      user.setForgottenPasswordTokenExpiresAt(
          new Date(System.currentTimeMillis() + expirationTime)); // set token with expiration
      userRepository.save(user);
      // String appUrl = request.getScheme() + "://" + request.getServerName();
      //       // scheme of our URL

      SimpleMailMessage passwordResetEmail = new SimpleMailMessage(); // created mail
      passwordResetEmail.setFrom("support@demo"); // set a emailFrom
      passwordResetEmail.setTo(user.getEmail());
      passwordResetEmail.setSubject("Password Reset Request");
      passwordResetEmail.setText(
          "To reset your password, click the link below:\n"
              + appUrl // need to set in constant
              + "/reset?token="
              + user.getForgottenPasswordToken());

      emailServiceImpl.sendEmail(passwordResetEmail);

      return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);
    }
  }

  @Override
  public ResponseEntity<? extends ResponseDto> resetPasswordViaToken(
      PasswordResetRequestDto resetPassword, String token) {
    User user = userRepository.findByForgottenPasswordToken(token); // finding a user by token
    Date currentDate = new Date(System.currentTimeMillis());
    if (userRepository.existsByForgottenPasswordToken(token)
        && currentDate.after(
            user
                .getForgottenPasswordTokenExpiresAt())) { // check current date with expiration and
                                                          // user
                                                          //              //compare user by token
      user.setPassword(resetPassword.password); // set new Password
      user.setForgottenPasswordToken(null); // reset token value to null
      userRepository.save(user);
      return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(
          new ErrorResponseDto("this session is expired"), HttpStatus.BAD_REQUEST);
    }
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
