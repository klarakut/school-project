package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleRequestDto {

    public final Long id;
    public final String role;

    //@JsonProperty(name="")

    @JsonCreator
    protected RoleRequestDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }
}
