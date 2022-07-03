package com.gfa.users.services;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
        ResponseEntity<? extends ResponseDto> resendVerificationEmail(EmailRequestDto emailRequestDto);
        public User existingUserByEmail(String email);
        ResponseEntity<ResponseDto> index();
        ResponseEntity<ResponseDto> show(Long id);
        ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);

}
