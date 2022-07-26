package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class TeamCreateRequestDto extends TeamDto implements RequestDto {

  public TeamCreateRequestDto(String name) {
    super(null, name);
  }
}
