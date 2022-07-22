package com.gfa.common.dtos;

import com.gfa.users.models.Role;

public class RoleResponseDto extends ResponseDto {

  public final Long id;
  public final String role;

  public RoleResponseDto(Long id, String role) {
    this.id = id;
    this.role = role;
  }

  public RoleResponseDto(Role role) {
    this.id = role.getId();
    this.role = role.getRole();
  }
}
