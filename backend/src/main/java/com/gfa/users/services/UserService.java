package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.dtos.CreateUserRequestDto;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  List<UserResponseDto> index();

  UserResponseDto store(CreateUserRequestDto dto);

  UserResponseDto show(Long id);

  StatusResponseDto resetPasswords(EmailRequestDto emailDto);

  StatusResponseDto resetPassword(String token, PasswordResetRequestDto resetPassword);

  StatusResponseDto resendVerificationEmail(EmailRequestDto emailRequestDto);

  Optional<User> findByUsername(String username);
}
