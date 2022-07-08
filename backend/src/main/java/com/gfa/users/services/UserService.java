package com.gfa.users.services;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService{ //extends UserDetailsService {

        ResponseEntity<ResponseDto> index();
        ResponseEntity<ResponseDto> show(Long id);
        ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);

        Optional<User> findByUsername(String username);
}
