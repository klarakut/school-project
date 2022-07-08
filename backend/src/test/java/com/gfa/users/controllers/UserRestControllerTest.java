package com.gfa.users.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class UserRestControllerTest {


    @Autowired
    private MockMvc mvc;


   @Test
    void post_register_new_user_test() throws Exception{
        //mvc.perform(MockMvcRequestBuilders.post("/register")
               mvc.perform(MockMvcRequestBuilders.post("/register")
                       .content("{\"username\":\"john\",\"email\":\"john@mail.com\",\"password\":\"1234\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].status").value("ok"));
    }
}