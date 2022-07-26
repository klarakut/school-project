package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class TeamRequestDto extends TeamDto implements RequestDto {
  public TeamRequestDto(String name) {
    super(null,name);
  }

}
