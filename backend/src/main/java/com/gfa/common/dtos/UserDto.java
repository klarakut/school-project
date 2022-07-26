package com.gfa.common.dtos;

public abstract class UserDto {
    public final String email;
    public final String username;
    public final String password;

    public UserDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
