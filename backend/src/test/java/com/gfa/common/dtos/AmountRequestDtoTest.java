package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmountRequestDtoTest {

  @Test
  void getAmount() {
    AmountRequestDto amountRequestDto = new AmountRequestDto(55L, 20);
    // assertEquals(20, amountRequestDto.getAmount());
  }
}
