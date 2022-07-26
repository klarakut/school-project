package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class TeamCreateRequestDto extends RequestDto {
  String name;

  public TeamCreateRequestDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
