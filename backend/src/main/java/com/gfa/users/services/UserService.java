package com.gfa.users.services;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.UserResponseDto;
import com.gfa.users.models.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;



public interface UserService {

        ResponseEntity<ResponseDto> index();
        ResponseEntity<ResponseDto> show(Long id);
        //ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);
        UserResponseDto store(CreateUserRequestDto dto);

        Optional<User> findByUsername(String username);

}
