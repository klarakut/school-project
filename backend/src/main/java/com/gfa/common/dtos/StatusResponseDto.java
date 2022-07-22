package com.gfa.common.dtos;

import com.gfa.common.dtos.ResponseDto;

public class StatusResponseDto extends ResponseDto {

  public final String status;

  public StatusResponseDto(String status) {
    this.status = status;
  }
}
