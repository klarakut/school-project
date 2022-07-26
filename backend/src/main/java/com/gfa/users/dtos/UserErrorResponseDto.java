package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.common.dtos.ResponseDto;

public class UserErrorResponseDto implements ResponseDto {

    String error;

    @JsonCreator
    public UserErrorResponseDto(String error) {
        this.error = error;
    }
}
