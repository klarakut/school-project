package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class RoleRequestDto extends RoleDto implements RequestDto{

  @JsonCreator
  protected RoleRequestDto(Long id, String role) {
    super(id,role);
  }
}
