package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;



class ErrorResponseDtoTest {

  @Test
  void can_create_dto() {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto("message");
    assertEquals("message", errorResponseDto.status);
  }
}
