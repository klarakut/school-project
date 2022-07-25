package com.gfa.common.dtos;

import com.gfa.users.models.Role;

public class RoleResponseDto extends RoleDto implements ResponseDto {
  public RoleResponseDto(Long id, String role) {
    super(id, role);
  }

  public RoleResponseDto(Role role) {
    super(role.getId(), role.getRole());
  }
}
