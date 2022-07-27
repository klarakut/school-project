package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserCreateRequestDto {
  public final String username;
  public final String email;
  public final String password;
  public boolean mfa;

  @JsonCreator
  public UserCreateRequestDto(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}