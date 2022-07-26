package com.gfa.users.dtos;

import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.models.User;
import java.time.LocalDateTime;

public class UserResponseDto extends ResponseDto {

  public final Long id;
  public final String username;
  public final String email;
  public final LocalDateTime verifiedAt;
  public final LocalDateTime createdAt;
  public final boolean mfa;
  public final String secretImageUri;

  public UserResponseDto(User user, String secretForImage) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.verifiedAt = user.getVerifiedAt();
    this.createdAt = user.getCreatedAt();
    this.mfa = user.isMfa();
    this.secretImageUri = secretForImage;
  }

  public UserResponseDto(User user, Boolean setNullVerificationAt) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    if (setNullVerificationAt) {
      this.verifiedAt = null;
    }
    else {
      this.verifiedAt = user.getVerifiedAt();
    }
    this.createdAt = user.getCreatedAt();
    this.mfa = user.isMfa();
    this.secretImageUri = user.getSecret();
  }
}
