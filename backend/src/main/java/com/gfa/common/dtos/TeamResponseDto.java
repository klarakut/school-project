package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public  class TeamResponseDto extends ResponseDto {

   public final Long id;
    public final String team;

    @JsonCreator
    public TeamResponseDto(Long id, String team) {
        this.id = id;
        this.team = team;
    }
}
