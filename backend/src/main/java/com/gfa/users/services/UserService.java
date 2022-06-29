package com.gfa.users.services;

import com.gfa.users.dtos.EmailRequestDto;
import com.gfa.users.dtos.ResponseDto;
import com.gfa.users.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto);

    public User existingUserByEmail(String email);
}
