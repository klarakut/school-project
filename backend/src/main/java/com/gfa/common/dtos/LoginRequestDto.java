package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LoginRequestDto {

  public String email;
  public String password;
  public String otp;

  @JsonCreator
    public LoginRequestDto(String email, String password, String otp) {
    this.email = email;
    this.password = password;
    this.otp = otp;
  }
}
