package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class TeamPatchRequestDto extends TeamDto implements RequestDto {

  public TeamPatchRequestDto(String name) {
    super(null, name);
  }
}
