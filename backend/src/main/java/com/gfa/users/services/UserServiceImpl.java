package com.gfa.users.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public ResponseEntity<? extends ResponseDto> resendVerifiEmail(EmailRequestDto emailRequestDto) {

        User user = userRepository.findByEmail(emailRequestDto);

        // #1
        //The specified email address is already verified results in “Email already verified!” error - 400
        boolean isAlreadyVerified = user.getVerified;

        if(isAlreadyVerified){
            new ResponseEntity<>(new Error("Email already verified"), HttpStatus.BAD_REQUEST);
        }
        // #2
        //The specified email address is invalid results in “Invalid email” error - 400
            boolean isValid = emailValidator(emailRequestDto);
        if(!isValid){
            new ResponseEntity<>(new Error("Invalid email"), HttpStatus.BAD_REQUEST);
        }
        // #3
        //The specified email address exists and is not yet verified results in “ok” status message (and the verification email is resent) - 200
        if(!isAlreadyVerified){
            // resend email steps

            return new ResponseEntity<? extends ResponseDto>(HttpStatus.OK);
        }
        // #4
        //The specified email address does not exist results in “ok” status message (but no email is sent - obviously) - 200

        return null;
    }
}
