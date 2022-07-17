package com.gfa.common.dtos;

public class EmptyResponseDto extends ResponseDto{
    public String status;

    public EmptyResponseDto(String status) {
        this.status = status;
    }
}
