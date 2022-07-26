package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.common.exceptions.UnknownErrorException;
import com.gfa.common.services.EmailValidator;
import com.gfa.users.dtos.*;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.exceptions.*;
import com.gfa.users.models.Team;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final String DEFAULT_TOKEN_EXPIRATION = "600";
  private final UserRepository userRepository;

  private final Environment environment;

  private final JwtTokenManager jwtTokenManager;
  private final TotpManager totpManager;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      Environment environment,
      JwtTokenManager jwtTokenManager,
      TotpManager totpManager) {
    this.userRepository = userRepository;
    this.environment = environment;
    this.jwtTokenManager = jwtTokenManager;
    this.totpManager = totpManager;
  }

  public UserResponseDto store(CreateUserRequestDto dto) {

    if (dto.username.isEmpty()) {
      throw new UsernameMissingException();
    }
    if (dto.password.isEmpty()) {
      throw new PasswordMissingException();
    }
    if (dto.email.isEmpty()) {
      throw new EmailMissingException();
    }

    boolean usernameExist = userRepository.findByUsername(dto.username).isPresent();
    if (usernameExist) {
      throw new UsernameTakenException();
    }

    if (dto.username.length() < 4) {
      throw new ShortUsernameException();
    }

    if (dto.password.length() < 8) {
      throw new ShortPasswordException();
    }

    EmailValidator.validate(dto.email);

    String secret = totpManager.generateSecret();
    Long tokenExpiration =
        Long.parseLong(
            environment.getProperty(
                "config.security.token.expiration.email_verification", DEFAULT_TOKEN_EXPIRATION));
    User user = new User(dto, tokenExpiration, secret);
    userRepository.save(user);
    UserResponseDto userResponseDto =
        new UserResponseDto(user, totpManager.getUriForImage(user.getSecret()));

    boolean userCreated = userRepository.findByUsername(dto.username).isPresent();
    if (!userCreated) {
      throw new UnexpectedErrorException();
    }
    return userResponseDto;
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
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
  public UserResponseDto update(Long id, UserPatchRequestDto userPatchRequestDto) {
    if (id <= 0) {
      throw new InvalidIdException();
    }
    if (userPatchRequestDto.getUsername().isEmpty()) {
      throw new InvalidRequestException();
    }
    try {
      User userUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
      userUpdate.setUsername(userPatchRequestDto.getUsername());
      if (!userPatchRequestDto.getPassword().isEmpty()) {
        if (userPatchRequestDto.getPassword().length() < 8) {
          throw new ShortPasswordException();
        }
        userUpdate.setPassword(userPatchRequestDto.getPassword());
      }
      if (!userPatchRequestDto.getEmail().isEmpty()) {            //pokud je zadán nový email tak tam nacpat ten secret a opakovat email validaci
        userUpdate.setEmail(userPatchRequestDto.getEmail());
      }
      String secretForImage = userUpdate.getSecret();
      return new UserResponseDto(userRepository.save(userUpdate), secretForImage);
    } catch (UserNotFoundException e) {
      throw new UserNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto destroy(Long id) {
    if (id <= 0) {
      throw new InvalidIdException();
    }
    try {
      User userDestroy = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
      userRepository.delete(userDestroy);
      return new EmptyResponseDto("ok");
    } catch (UserNotFoundException e) {
      throw new UserNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
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
