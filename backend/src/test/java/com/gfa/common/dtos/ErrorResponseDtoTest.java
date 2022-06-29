package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseDtoTest {

  @Test
  void getMessage() {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto("message", "email@email.com");
    assertEquals("message", errorResponseDto.getMessage());
  }

  @Test
  void getEmail() {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto("message", "email@email.com");
    assertEquals("email@email.com", errorResponseDto.getEmail());
  }
}