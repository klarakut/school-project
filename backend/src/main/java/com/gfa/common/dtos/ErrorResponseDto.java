package com.gfa.common.dtos;

public class ErrorResponseDto implements ResponseDto {
  public final String status;

  public ErrorResponseDto(String status) {
    this.status = status;
  }
}
