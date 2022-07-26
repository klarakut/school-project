package com.gfa.users.dtos;

import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.models.Team;

public class TeamResponseDto extends TeamDto implements ResponseDto {

  public TeamResponseDto(Team team) {
    super(team.getId(), team.getName());
  }
}
