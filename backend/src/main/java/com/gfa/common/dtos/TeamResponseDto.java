package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.users.models.Team;

public  class TeamResponseDto extends ResponseDto {

   public final Long id;
    public final String team;


    public TeamResponseDto(Team team) {
        this.id = team.getId();
        this.team = team.getName();
    }
}
