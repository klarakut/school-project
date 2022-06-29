package com.gfa.users.controllers;

import com.gfa.users.dtos.EmailRequestDto;
import com.gfa.users.dtos.ResponseDto;
import com.gfa.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    /*private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<? extends ResponseDto> resendVerificationEmail(@RequestBody EmailRequestDto dto){
        return userService.resendVerificationEmail(dto);
    }*/
}
