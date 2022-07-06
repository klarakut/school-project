package com.gfa.common.dtos;

public class ErrorResponseDto {
  public final String message;

  @JsonCreator
  public ErrorResponseDto(String message) {
    this.message = message;
  }
}
