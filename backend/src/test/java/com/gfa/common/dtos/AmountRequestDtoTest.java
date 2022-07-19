package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountRequestDtoTest {

  @Test
  void can_create_dto() {
    AmountRequestDto amountRequestDto = new AmountRequestDto(55L, 20);
    //assertEquals(20, amountRequestDto.getAmount());
  }
}
