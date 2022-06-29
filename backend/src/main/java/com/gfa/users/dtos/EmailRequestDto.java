package com.gfa.users.dtos;

public class EmailRequestDto extends ResponseDto {

    public String email;


    public EmailRequestDto(String email) {
        this.email = email;
    }
}
