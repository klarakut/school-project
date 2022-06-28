package com.gfa.common.dtos;

public class ErrorResponseDto extends ResponseDto{

    public final String error;

    public ErrorResponseDto(String error) {
        this.error = error;
    }
}
