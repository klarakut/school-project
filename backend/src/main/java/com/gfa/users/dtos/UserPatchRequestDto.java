package com.gfa.users.dtos;

public class UserPatchRequestDto {
    String email;
    String username;
    String password;

    public UserPatchRequestDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
