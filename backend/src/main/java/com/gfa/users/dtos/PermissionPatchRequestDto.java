package com.gfa.users.dtos;

public class PermissionPatchRequestDto {
  public final String ability;

  public PermissionPatchRequestDto(String ability) {
    this.ability = ability;
  }

  public String getAbility() {
    return ability;
  }
}
