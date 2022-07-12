package com.gfa.common.dtos;

public class RoleResponseDto extends ResponseDto {

    public final Long id;
    public final String role;

    public RoleResponseDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }
}
