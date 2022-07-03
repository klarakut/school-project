package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.jetbrains.annotations.NotNull;

public class CreateUserRequestDto {

    @NotNull
    public final String username;
    @NotNull
    public final String email;
    @NotNull
    public final String password;

    @JsonCreator
    public CreateUserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
