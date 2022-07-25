package com.gfa.common.dtos;

public class JwtResponseDto extends ResponseDto{

    public String token;

    public JwtResponseDto(String token) {
        this.token = token;
    }
}
