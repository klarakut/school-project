package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

class IdRequestDto implements RequestDto {
  public final Long id;

  @JsonCreator
  public IdRequestDto(Long id) {
    this.id = id;
  }
}
