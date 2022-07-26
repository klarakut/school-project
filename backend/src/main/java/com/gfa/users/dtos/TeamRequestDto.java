package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;

public class TeamRequestDto extends RequestDto {
  String team;

  public TeamRequestDto(String team) {
    this.team = team;
  }

  public String getTeam() {
    return team;
  }
}
