package com.gfa.users.dtos;

public class TeamCreateRequestDto {
  String name;

  public TeamCreateRequestDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
