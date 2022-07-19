package com.gfa.users.dtos;

public class TeamPatchRequestDto {
  String name;

  public TeamPatchRequestDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
