package com.gfa.users.controllers;

import com.gfa.users.dtos.EmailRequestDto;
import com.gfa.users.dtos.ResponseDto;
import com.gfa.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/email/verify/resend")
    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto){
        return userService.resendVerificationEmail(emailRequestDto);
    }
}
