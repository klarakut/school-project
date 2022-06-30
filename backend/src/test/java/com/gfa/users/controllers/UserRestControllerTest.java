package com.gfa.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc
class UserRestControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }


  User Martin = new User("Martin", "email", "password", new Date(System.currentTimeMillis()));
  User Michal =
      new User("Michal", "michal@gmail.com", "JavaLOver", new Date(System.currentTimeMillis()));
  User Klara =
      new User("Klara", "klarakut@seznam.com", "UnitLover", new Date(System.currentTimeMillis()));

  EmailRequestDto email = new EmailRequestDto("Gregorovic@gmail.com");



  @Test
  void store() {}

  @Test
  public void resetPassword() throws Exception {
    

  }

  @Test
  void resetPasswordViaSendToken() {}
}
