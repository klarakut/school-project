package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class PermissionCreateRequestDto extends RequestDto {
  public final String ability;

  public PermissionCreateRequestDto(String ability) {
    this.ability = ability;
  }

  public String getAbility() {
    return ability;
  }
}
