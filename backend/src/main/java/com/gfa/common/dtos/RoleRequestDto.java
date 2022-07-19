package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class RoleRequestDto {
  public final Long id;
  public final String role;

  @JsonCreator
  protected RoleRequestDto(Long id, String role) {
    this.id = id;
    this.role = role;
  }
}
