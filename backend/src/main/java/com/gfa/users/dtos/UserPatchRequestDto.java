package com.gfa.users.dtos;

import com.gfa.common.dtos.RequestDto;
import com.gfa.common.dtos.UserDto;

public class UserPatchRequestDto extends UserDto implements RequestDto {

  public UserPatchRequestDto(String email, String username, String password) {
    super(email, username, password);
  }
}

