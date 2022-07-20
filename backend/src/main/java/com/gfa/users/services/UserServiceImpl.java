package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.common.exceptions.UnknownErrorException;
import com.gfa.common.services.EmailValidator;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.dtos.CreateUserRequestDto;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.exceptions.PasswordTooShortException;
import com.gfa.users.exceptions.AlreadyVerifiedException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final String DEFAULT_TOKEN_EXPIRATION = "600";
  private final UserRepository userRepository;

  private final Environment environment;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, Environment environment) {
    this.userRepository = userRepository;
    this.environment = environment;
  }

  public UserResponseDto store(CreateUserRequestDto dto) {

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

    EmailValidator.validate(dto.email);

    Long tokenExpiration =
        Long.parseLong(
            environment.getProperty(
                "config.security.token.expiration.email_verification", DEFAULT_TOKEN_EXPIRATION));
    User user = new User(dto, tokenExpiration);
    userRepository.save(user);
    UserResponseDto userResponseDto = new UserResponseDto(user);

    boolean userCreated = userRepository.findByUsername(dto.username).isPresent();
    if (!userCreated) {
      ErrorResponseDto error = new ErrorResponseDto("Unknown error");
      throw new RuntimeException();
    }
    return userResponseDto;
  }

  @Override
  public List<UserResponseDto> index() {
    return null;
  }

  @Override
  public UserResponseDto show(Long id) {
    return null;
  }

  @Override
  public StatusResponseDto resetPasswords(EmailRequestDto emailDto) {

    EmailValidator.validate(emailDto.email);

    User user = userRepository.findByEmail(emailDto.email).orElseThrow(UserNotFoundException::new);
    if (user == null) {
      return new StatusResponseDto("ok");
    }
    if (user.getVerifiedAt() == null) {
      throw new UnverifiedEmailException();
    }
    return null;
  }

  @Override
  public StatusResponseDto resetPassword(String token, PasswordResetRequestDto resetPassword) {

    User user =
        userRepository.findByForgottenPasswordToken(token).orElseThrow(InvalidTokenException::new);

    if (resetPassword.password.isEmpty()) {
      throw new InvalidPasswordException();
    }
    LocalDateTime expiration = user.getForgottenPasswordTokenExpiresAt();
    if (expiration == null || LocalDateTime.now().isAfter(expiration)) {
      throw new TokenExpiredException();
    }
    if (resetPassword.password.length() <= 8) {
      throw new PasswordTooShortException();
      /*
         emailUtils.sendHtmlEmail(user.getEmail(), "support@demo", "Password Reset Request", "To reset your password, click the link below:\n"
                 + "http://localhost:3036/email/reset-password"
         + "/reset?token="
                 + user.getForgottenPasswordToken());
      */
    }
    String forgottenPasswordToken = user.getForgottenPasswordToken();
    if (forgottenPasswordToken == null || !forgottenPasswordToken.equals(token)) {
      throw new InvalidTokenException();
    }
    user.setPassword(resetPassword.password);
    user.setForgottenPasswordToken(null);
    userRepository.save(user);
    return new StatusResponseDto("ok");
  }

  @Override
  public StatusResponseDto resendVerificationEmail(EmailRequestDto emailRequestDto) {
    EmailValidator.validate(emailRequestDto.email);

    boolean existingUser = userRepository.findByEmail(emailRequestDto.email).isPresent();
    if (!existingUser) {
      /* emailUtils.sendHtmlEmail("#", "support@demo.com", "Resend verification email", "To verify your email address, click the link below:\n"
      + "http://localhost:3036/email/resend-verification-email"
      + "/reset?token="
      + user.getVerificationToken());*/
      return new StatusResponseDto("ok");
    }

    User user = userRepository.findByEmail(emailRequestDto.email).get();

    if (user.getVerifiedAt() != null) {
      throw new AlreadyVerifiedException();
    }

    try {
      /*emailUtils.sendHtmlEmail(user.getEmail(), "support@demo.com", "Resend verification email", "To verify your email address, click the link below:\n"
      + "http://localhost:3036/email/resend-verification-email"
      + "/reset?token="
      + user.getVerificationToken());*/
      return new StatusResponseDto("ok");
    } catch (UnknownErrorException e) {
      throw new UnknownErrorException();
    }
  }
}
