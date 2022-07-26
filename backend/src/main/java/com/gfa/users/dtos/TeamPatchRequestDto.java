package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class TeamPatchRequestDto extends RequestDto {
  String name;

  public TeamPatchRequestDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
