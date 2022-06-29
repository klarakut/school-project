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

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User existingUserByEmail(String email){  //I not sure if this part (method) is necessary
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.orElse(null);
    }
    @Override
    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto) {

        User user = existingUserByEmail(emailRequestDto.email);

        // #4
        if (user == null){
            return new ResponseEntity<>(HttpStatus.OK);
            //send an email to “random” (or designated) email address
        }

        // #1
        if(user.getVerifiedAt() != null){
           return new ResponseEntity<>(new ErrorResponseDto("Email already verified"), HttpStatus.BAD_REQUEST);
        }

        // #2
        /*boolean isValid = emailValidator(emailRequestDto);
        if(!isValid){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST);
        }*/

        // #3
        if(user.getVerifiedAt() == null){
            return new ResponseEntity<>(HttpStatus.OK);
            //resend email
        }

        //#5
        return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
