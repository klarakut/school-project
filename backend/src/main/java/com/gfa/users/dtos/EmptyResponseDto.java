package com.gfa.users.dtos;

import com.gfa.common.dtos.ResponseDto;

public class EmptyResponseDto implements ResponseDto {
  public String status;

  public EmptyResponseDto(String status) {
    this.status = status;
  }
}
