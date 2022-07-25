package com.gfa.users.services;

import com.gfa.common.dtos.JwtResponseDto;
import com.gfa.common.dtos.LoginRequestDto;

public interface LoginService {
    JwtResponseDto login(LoginRequestDto dto);
}
