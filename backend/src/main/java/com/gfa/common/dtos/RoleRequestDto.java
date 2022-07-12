package com.gfa.common.dtos;

public class RoleRequestDto {
    Long id;
    String role;

    public RoleRequestDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
