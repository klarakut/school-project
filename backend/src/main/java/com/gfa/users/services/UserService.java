package com.gfa.users.services;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto);
}
