package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class PermissionRequestDto extends PermissionDto implements RequestDto {
  public PermissionRequestDto(Long id, String ability) {
    super(id, ability);
  }
}
