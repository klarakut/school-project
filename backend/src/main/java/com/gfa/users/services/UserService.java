package com.gfa.users.services;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.PasswordResetRequestDto;
import com.gfa.common.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;


public interface UserService{ //extends UserDetailsService {

        ResponseEntity<ResponseDto> index();
        ResponseEntity<ResponseDto> show(Long id);
        ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);

        ResponseEntity<? extends ResponseDto> resetPasswords(EmailRequestDto emailDto);
        ResponseEntity<? extends ResponseDto> resetPasswordViaToken(String token, PasswordResetRequestDto resetPassword);



}
