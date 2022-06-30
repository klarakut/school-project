package com.gfa.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserRestControllerTest {



  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean  UserRepository userRepository;
  @MockBean
  EmailRequestDto emailDto;

  User Martin = new User("Martin","email","password",new Date(System.currentTimeMillis()));
  User Michal = new User("Michal", "michal@gmail.com", "JavaLOver",new Date(System.currentTimeMillis()));
  User Klara = new User("Klara", "klarakut@seznam.com","UnitLover",new Date(System.currentTimeMillis()));

  EmailRequestDto email = new EmailRequestDto("Gregorovic@gmail.com");

  @Test
  void shouldCreateMockMvc() {
    assertNotNull(mockMvc);
  }

  @Test
  void store() {}

  @Test
  void resetPassword() throws Exception {
    
  }

  @Test
  void resetPasswordViaSendToken() {}
}
