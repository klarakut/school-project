package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ErrorResponseDto extends ResponseDto{
  public final String message;

  @JsonCreator
  public ErrorResponseDto(String message) {
    this.message = message;
  }
}
