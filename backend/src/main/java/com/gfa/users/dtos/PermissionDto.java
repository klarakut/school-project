package com.gfa.users.dtos;

public abstract class PermissionDto {
  public final Long id;
  public final String ability;

  public PermissionDto(Long id, String ability) {
    this.id = id;
    this.ability = ability;
  }
}
