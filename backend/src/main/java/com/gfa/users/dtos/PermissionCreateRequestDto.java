package com.gfa.users.dtos;

public class PermissionCreateRequestDto {
    public final String ability;

    public PermissionCreateRequestDto(String ability) {
        this.ability = ability;
    }

    public String getAbility() {
        return ability;
    }
}
