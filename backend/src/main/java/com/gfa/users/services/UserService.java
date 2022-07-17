package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.dtos.CreateUserRequestDto;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import java.util.List;

public interface UserService { // extends UserDetailsService {

  List<UserResponseDto> index();

<<<<<<< HEAD
 // ResponseEntity<ResponseDto> index();


        ResponseEntity<ResponseDto> index();
        ResponseEntity<ResponseDto> show(Long id);
        ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto);

        StatusResponseDto resetPasswords(EmailRequestDto emailDto);
        StatusResponseDto resetPassword(String token, PasswordResetRequestDto resetPassword);
=======
  UserResponseDto show(Long id);
>>>>>>> develop

  UserResponseDto store(CreateUserRequestDto dto);

  StatusResponseDto resetPasswords(EmailRequestDto emailDto);

  StatusResponseDto resetPassword(String token, PasswordResetRequestDto resetPassword);
}
