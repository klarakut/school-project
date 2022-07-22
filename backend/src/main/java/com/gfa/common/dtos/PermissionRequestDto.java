package com.gfa.common.dtos;

public class PermissionRequestDto {

  public final Long id;
  public final String ability;

  public PermissionRequestDto(Long id, String ability) {
    this.id = id;
    this.ability = ability;
  }
}
