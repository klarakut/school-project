package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class PermissionPatchRequestDto extends PermissionDto implements RequestDto {
  public PermissionPatchRequestDto(String ability) {
    super(null, ability);
  }
}
