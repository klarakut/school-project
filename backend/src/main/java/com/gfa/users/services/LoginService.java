package com.gfa.users.services;

import com.gfa.common.dtos.LoginRequestDto;
import com.gfa.common.dtos.StatusResponseDto;

public interface LoginService {
  StatusResponseDto login(LoginRequestDto dto);
}
