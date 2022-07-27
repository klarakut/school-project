package com.gfa.common.services;

import com.gfa.users.dtos.UserPatchRequestDto;
import com.gfa.users.exceptions.InvalidRequestException;

public class UpdateUserValidator {

  public static void validate(UserPatchRequestDto userPatchRequestDto) {
    if (userPatchRequestDto.username == null) {
      throw new InvalidRequestException();
    }
    if (userPatchRequestDto.email == null) {
      throw new InvalidRequestException();
    }
    if (userPatchRequestDto.password == null) {
      throw new InvalidRequestException();
    }
    if (userPatchRequestDto.username.isEmpty()) {
      throw new InvalidRequestException();
    }
    if (userPatchRequestDto.email.isEmpty()) {
      throw new InvalidRequestException();
    }
    if (userPatchRequestDto.password.isEmpty()) {
      throw new InvalidRequestException();
    }
  }

}
