package com.gfa.users.services;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService { // extends UserDetailsService {

  ResponseEntity<ResponseDto> index();

  ResponseEntity<ResponseDto> show(Long id);

  ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);
}
