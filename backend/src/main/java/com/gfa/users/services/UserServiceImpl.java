package com.gfa.users.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User existingUserByEmail(EmailRequestDto email){
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.orElse(null);
    }
    @Override
    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto) {

        User user = existingUserByEmail(emailRequestDto);
                // userRepository.findByEmail(emailRequestDto);

        // #4
        //The specified email address does not exist results in “ok” status message (but no email is sent - obviously) - 200
        if (user == null){
            //resend email
            return new ResponseEntity<? extends ResponseDto>(HttpStatus.OK);
        }

        // #1
        //The specified email address is already verified results in “Email already verified!” error - 400
        boolean isAlreadyVerified = user.getEnabled;

        if(isAlreadyVerified){
           return new ResponseEntity<? extends ResponseDto>(new Error("Email already verified"), HttpStatus.BAD_REQUEST);
        }
        // #2
        //The specified email address is invalid results in “Invalid email” error - 400
            boolean isValid = emailValidator(emailRequestDto);
        if(!isValid){
            return new ResponseEntity<? extends ResponseDto>(new Error("Invalid email"), HttpStatus.BAD_REQUEST);
        }
        // #3
        //The specified email address exists and is not yet verified results in “ok” status message (and the verification email is resent) - 200
        if(!isAlreadyVerified){
            // resend email steps

            /*
            String appUrl =
                    "http://" + request.getServerName() +
                    ":" + request.getServerPort() +
                    request.getContextPath();
            SimpleMailMessage email =
                constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
                mailSender.send(email);
             */




            return new ResponseEntity<? extends ResponseDto>(HttpStatus.OK);
        }

        return new ResponseEntity<? extends ResponseDto>(new Error("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    
}
