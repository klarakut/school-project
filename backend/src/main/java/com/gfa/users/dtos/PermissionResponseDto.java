package com.gfa.users.dtos;

import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.models.Permission;

public class PermissionResponseDto extends ResponseDto {
  public final Long id;
  public final String ability;

  public PermissionResponseDto(Permission permission) {
    this.id = permission.getId();
    this.ability = permission.getAbility();
  }
}
