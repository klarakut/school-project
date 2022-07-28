package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.StatusResponseDto;

public interface EmailVerificationService {
  StatusResponseDto resend(EmailRequestDto dto);

  public StatusResponseDto verify(String token);
}
