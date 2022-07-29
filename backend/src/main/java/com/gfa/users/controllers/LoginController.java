package com.gfa.users.controllers;

import com.gfa.common.dtos.LoginRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private final LoginService loginService;

  @Autowired
  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<? extends ResponseDto> login(@RequestBody LoginRequestDto dto) {

    StatusResponseDto response = loginService.login(dto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
