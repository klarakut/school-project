package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdRequestDtoTest {

  @Test
  void can_create_dto() {
    IdRequestDto idRequestDto = new IdRequestDto(55L);

    assertEquals(55L, idRequestDto.id);

  }
}
