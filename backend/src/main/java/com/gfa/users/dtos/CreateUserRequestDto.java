package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CreateUserRequestDto {
  public final String username;
  public final String email;
  public final String password;

  @JsonCreator
  public CreateUserRequestDto(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
