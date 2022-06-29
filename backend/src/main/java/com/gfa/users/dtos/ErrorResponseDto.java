package com.gfa.users.dtos;

public class ErrorResponseDto extends ResponseDto{

    public String error;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(String error) {
        this.error = error;
    }
}
