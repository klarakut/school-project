package com.gfa.users.dtos;

import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.models.Permission;

public class PermissionResponseDto extends PermissionDto implements ResponseDto {
  public PermissionResponseDto(Permission permission) {
    super(permission.getId(), permission.getAbility());
  }
}
