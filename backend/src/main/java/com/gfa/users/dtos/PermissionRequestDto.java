package com.gfa.users.dtos;

public class PermissionRequestDto {
  Long id;
  String ability;

  public PermissionRequestDto(Long id, String ability) {
    this.id = id;
    this.ability = ability;
  }

  public Long getId() {
    return id;
  }

  public String getAbility() {
    return ability;
  }
}
