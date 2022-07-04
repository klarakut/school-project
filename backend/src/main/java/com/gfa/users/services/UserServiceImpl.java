package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.models.User;

import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EmailValidator emailValidator;
    private final EmailUtils emailUtils;

    public UserServiceImpl(UserRepository userRepository,EmailValidator emailValidator,EmailUtils emailUtils) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.emailUtils = emailUtils;
    }

    
    @Override
    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto) {
        //#1
        boolean isValid = emailValidator.isValid(emailRequestDto.email);
        if(!isValid){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findUserByEmail(emailRequestDto.email);

        //#2
        if (user == null){
            emailUtils.sendHtmlEmail("#", "support@demo.com", "Resend verification email", "To verify your email address, click the link below:\n"
                    + "http://localhost:3036/email/resend-verification-email"
            + "/reset?token="
                    + user.getVerificationToken());
            return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);
        }

        //#3
        if(user.getVerifiedAt() != null){
            if(user.getVerifiedAt().after(new Date(System.currentTimeMillis()))){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
           return new ResponseEntity<>(new ErrorResponseDto("Email already verified"), HttpStatus.BAD_REQUEST);
        }

        //#4
        if(user.getVerifiedAt() == null){
            emailUtils.sendHtmlEmail(user.getEmail(), "support@demo.com", "Resend verification email", "To verify your email address, click the link below:\n"
                    + "http://localhost:3036/email/resend-verification-email"
                    + "/reset?token="
                    + user.getVerificationToken());
            return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto){

        if(dto.username.isEmpty()){
            return new ResponseEntity<>(new ErrorResponseDto("Username is required"), HttpStatus.BAD_REQUEST);
        }
        if(dto.password.isEmpty()){
            return new ResponseEntity<>(new ErrorResponseDto("Password is required"), HttpStatus.BAD_REQUEST);
        }
        if(dto.email.isEmpty()){
            return new ResponseEntity<>(new ErrorResponseDto("Email is required"), HttpStatus.BAD_REQUEST);
        }

        boolean usernameExist = userRepository.findByUsername(dto.username).isPresent();
        if(usernameExist){
            ErrorResponseDto error = new ErrorResponseDto("Username is already taken");
            return new ResponseEntity<>(error,HttpStatus.CONFLICT);
        }

        if(dto.username.length() < 4){
            ErrorResponseDto error = new ErrorResponseDto("Username must be at least 4 characters long");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }

        if(dto.password.length() < 8){
            ErrorResponseDto error = new ErrorResponseDto("Password must be at least 8 characters long");
            return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
        }

        boolean isValidEmail = emailValidator.isValid(dto.email);
        if(!isValidEmail){
            ErrorResponseDto error = new ErrorResponseDto("Invalid email");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User user = new User(dto);
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return new ResponseEntity<UserResponseDto>(userResponseDto,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDto> index() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> show(Long id) {
        return null;
    }
}
