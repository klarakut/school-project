package com.gfa.users.dtos;

import com.gfa.common.dtos.ResponseDto;

public class PermissionErrorResponseDto extends ResponseDto {
    String error;

    public PermissionErrorResponseDto(String error) {
        this.error = error;
    }
}

