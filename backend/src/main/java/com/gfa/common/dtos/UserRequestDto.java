package com.gfa.common.dtos;

public class UserRequestDto {
    Long id;
    String username;

    public UserRequestDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
