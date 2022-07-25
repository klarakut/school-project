package com.gfa.common.dtos;

public abstract class RoleDto {
  public final Long id;
  public final String role;

  public RoleDto(Long id, String role) {
    this.id = id;
    this.role = role;
  }
}
