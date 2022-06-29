package com.gfa.users.controllers;


import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController  {
    @Autowired
    private final UserService userService;

    public ResetPasswordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<? extends ResponseDto> resetPassword(@RequestBody EmailRequestDto emailDto){
        return userService.findUserByEmail(emailDto);
    }



}
