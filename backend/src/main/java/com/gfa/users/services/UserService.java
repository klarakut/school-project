package com.gfa.users.services;

import com.gfa.common.dtos.*;
import org.springframework.http.ResponseEntity;


public interface UserService {

        StatusResponseDto resendVerificationEmail(EmailRequestDto emailRequestDto);
        ResponseEntity<ResponseDto> index();
        ResponseEntity<ResponseDto> show(Long id);
        ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);

        StatusResponseDto resetPasswords(EmailRequestDto emailDto);
        StatusResponseDto resetPassword(String token, PasswordResetRequestDto resetPassword);
}
