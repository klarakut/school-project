package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdRequestDtoTest {

  @Test
  void getId() {
    IdRequestDto idRequestDto = new IdRequestDto(55L);
   // assertEquals(55L, idRequestDto.getId());
  }
}
