package com.gfa.common.dtos;

import com.gfa.users.dtos.UserCreateRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserRequestDtoTest {

  @Test
  void can_create_dto() {
    UserCreateRequestDto createUserRequestDtoTest =
        new UserCreateRequestDto("alex", "alex@gmail.com", "abc");
    assertEquals("alex", createUserRequestDtoTest.username);
  }
}
