package com.gfa.common.dtos;

public class ErrorResponseDto extends ResponseDto {
  public final String status;

  public ErrorResponseDto(String status) {
    this.status = status;
  }
}
