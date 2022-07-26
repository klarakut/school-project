package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.common.dtos.ResponseDto;

public class TeamErrorResponseDto implements ResponseDto {

  String error;

  @JsonCreator
  public TeamErrorResponseDto(String error) {
    this.error = error;
  }
}
