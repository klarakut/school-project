package com.gfa.users.controllers;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.EmailRequestDto;

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

    @PostMapping("/register")
    public ResponseEntity<? extends ResponseDto> store(@RequestBody CreateUserRequestDto dto){
        return userService.store(dto);
    }

    @PostMapping("/email/verify/resend")
    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto){
        return userService.resendVerificationEmail(emailRequestDto);
    }
}
