package com.gfa.users.dtos;

import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.models.Team;

public class TeamResponseDto implements ResponseDto {

  public final Long id;
  public final String team;

  public TeamResponseDto(Team team) {
    this.id = team.getId();
    this.team = team.getName();
  }
}
