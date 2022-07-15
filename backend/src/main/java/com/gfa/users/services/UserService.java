package com.gfa.users.services;

import com.gfa.users.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService { // extends UserDetailsService {

  ResponseEntity<ResponseDto> index();

  ResponseEntity<ResponseDto> show(Long id);

  ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);
}
