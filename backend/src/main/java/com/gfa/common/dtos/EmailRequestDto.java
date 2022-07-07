package com.gfa.common.dtos;

public class EmailRequestDto {
  public final String email;

  @JsonCreator
  public EmailRequestDto(String email) {
    this.email = email;
  }
}
