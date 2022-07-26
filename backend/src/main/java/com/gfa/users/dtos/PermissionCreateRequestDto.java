package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class PermissionCreateRequestDto extends PermissionDto implements RequestDto {
  public PermissionCreateRequestDto(String ability) {
    super(null, ability);
  }
}
