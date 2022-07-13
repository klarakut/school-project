package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;

import com.gfa.common.dtos.ErrorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final Long tokenExpiration;
    //private final EmailUtils emailUtils;

  @Autowired
  public UserServiceImpl(
      EmailValidator emailValidator,
      UserRepository userRepository,
      @Value("${token_expiration}") Long tokenExpiration) {
    this.emailValidator = emailValidator;
    this.userRepository = userRepository;
    this.tokenExpiration = tokenExpiration;
  }

    @Override
    public StatusResponseDto resendVerificationEmail(EmailRequestDto emailRequestDto) {
        //#1
        boolean isValid = emailValidator.isValid(emailRequestDto.email);
        if(!isValid || emailRequestDto.email.isEmpty()){
            throw new InvalidEmailException();
        }

        User user = userRepository.findByEmail(emailRequestDto.email);

        /*
        //#2
        if (user == null){
            emailUtils.sendHtmlEmail("#", "support@demo.com", "Resend verification email", "To verify your email address, click the link below:\n"
                    + "http://localhost:3036/email/resend-verification-email"
                    + "/reset?token="
                    + user.getVerificationToken());
            return new StatusResponseDto("ok");
        }*/

        //#3
        if(user.getVerifiedAt() != null){
            throw new AlreadyVerifiedException();
        }

        //#4
        /*emailUtils.sendHtmlEmail(user.getEmail(), "support@demo.com", "Resend verification email", "To verify your email address, click the link below:\n"
                + "http://localhost:3036/email/resend-verification-email"
                + "/reset?token="
                + user.getVerificationToken());*/
        return new StatusResponseDto("ok");
    }

    public ResponseEntity<? extends ResponseDto> store (CreateUserRequestDto dto){

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
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
            //throw new RuntimeException();
        }

        if (dto.username.length() < 4) {
            ErrorResponseDto error = new ErrorResponseDto("Username must be at least 4 characters long");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            //throw new RuntimeException();
        }

        if (dto.password.length() < 8) {
            ErrorResponseDto error = new ErrorResponseDto("Password must be at least 8 characters long");
            throw new RuntimeException();
            //throw new RuntimeException();
        }

        boolean isValidEmail = emailValidator.isValid(dto.email);
        if (!isValidEmail) {
            ErrorResponseDto error = new ErrorResponseDto("Invalid email");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            //throw new RuntimeException();
        }

        // String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        // user.setPassword(encodedPassword);

        User user = new User(dto,tokenExpiration);
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.CREATED);
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



  public StatusResponseDto resetPassword (String token, PasswordResetRequestDto resetPassword){
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
    public ResponseEntity<ResponseDto> index () {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> show (Long id){
        return null;
    }
    }