package com.gfa.users.dtos;

public abstract class TeamDto {
  public final Long id;
  public final String name;

  public TeamDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
