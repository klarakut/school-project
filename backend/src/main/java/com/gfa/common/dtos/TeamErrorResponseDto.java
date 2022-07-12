package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TeamErrorResponseDto extends TeamResponseDto{

    String error;

    @JsonCreator
    public TeamErrorResponseDto(String error) {
        this.error = error;
    }
}
