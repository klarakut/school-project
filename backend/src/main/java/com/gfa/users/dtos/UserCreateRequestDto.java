package com.gfa.users.dtos;

public class UserCreateRequestDto {
    String username;
    String email;
    String password;

    public UserCreateRequestDto(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
