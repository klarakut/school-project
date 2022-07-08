package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.PasswordResetRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /*
     @GetMapping("/")
  public ResponseEntity<ResponseDto> index(){
    return service.index();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto> show(@PathVariable Long id){
    return service.show(id);
  }
     */



}
