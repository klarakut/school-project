package com.gfa.common.dtos;

class IdRequestDto extends RequestDto {
  public final Long id;

  @JsonCreator
  public IdRequestDto(Long id) {
    this.id = id;
  }
}
