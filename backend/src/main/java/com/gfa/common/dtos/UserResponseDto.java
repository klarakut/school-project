package com.gfa.common.dtos;

import com.gfa.users.models.User;

import java.util.Date;

public class UserResponseDto extends ResponseDto{

    public final Long id;
    public final String username;
    public final String email;
    public final Date verifiedAt;
    public final Date createdAt;
    public final boolean mfa;
    public final String secretImageUri;


    public UserResponseDto(User user, String secret) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.verifiedAt = user.getVerifiedAt();
        this.createdAt = user.getCreatedAt();
        this.mfa = user.isMfa();
        this.secretImageUri = secret;
    }
}
