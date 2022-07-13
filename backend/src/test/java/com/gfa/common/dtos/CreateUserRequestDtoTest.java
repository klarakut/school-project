package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserRequestDtoTest {

  @Test
  void can_create_dto() {
    CreateUserRequestDto createUserRequestDtoTest =
        new CreateUserRequestDto("alex", "alex@gmail.com", "abc");
    assertEquals("alex", createUserRequestDtoTest.username);
  }
}
