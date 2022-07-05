package com.gfa.users.services;

import com.gfa.common.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;


public interface UserService{ //extends UserDetailsService {

        ResponseEntity<ResponseDto> index();
        ResponseEntity<ResponseDto> show(Long id);
        ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);

        StatusResponseDto resetPasswords(EmailRequestDto emailDto);
        StatusResponseDto resetPassword(String token, PasswordResetRequestDto resetPassword);


}
