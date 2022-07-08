package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.jetbrains.annotations.NotNull;

public class CreateUserRequestDto {

    public final String username;
    public final String email;
    public final String password;
    public boolean mfa = true;

    @JsonCreator
    public CreateUserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
