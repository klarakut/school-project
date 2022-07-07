package com.gfa.users.controllers;

import com.gfa.users.services.UserService;

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
