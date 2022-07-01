package com.gfa.users.services;

import com.gfa.users.dtos.EmailRequestDto;
import com.gfa.users.dtos.ErrorResponseDto;
import com.gfa.users.dtos.ResponseDto;
import com.gfa.users.models.User;
import com.gfa.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EmailValidator emailValidator;

    public UserServiceImpl(UserRepository userRepository,EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
    }

    @Override
    public User existingUserByEmail(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.orElse(null);
    }

    @Override
    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto) {
        //#1
        boolean isValid = emailValidator.isValid(emailRequestDto.email);
        if(!isValid){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findUserByEmail(email);

        //#2
        if (user == null){
            //send an email to “random” (or designated) email address
            return new ResponseEntity<? extends ResponseDto>(HttpStatus.OK);
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
            //resend email
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
