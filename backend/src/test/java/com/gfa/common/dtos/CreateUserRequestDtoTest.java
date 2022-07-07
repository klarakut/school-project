package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserRequestDtoTest {

    @Test
    void can_create_createUserRequestDto(){
        CreateUserRequestDto createUserRequestDtoTest = new CreateUserRequestDto("alex","alex@gmail.com","abc");
        assertEquals("alex",createUserRequestDtoTest.username);
    }

}