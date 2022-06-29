package com.gfa.common.dtos;

class ErrorResponseDto {
  public final String message;

  @JsonCreator
  public ErrorResponseDto(String message) {
    this.message = message;
  }
}
