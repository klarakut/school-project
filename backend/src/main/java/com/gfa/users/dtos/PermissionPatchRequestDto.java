package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class PermissionPatchRequestDto extends RequestDto {
  public final String ability;

  public PermissionPatchRequestDto(String ability) {
    this.ability = ability;
  }

  public String getAbility() {
    return ability;
  }
}
