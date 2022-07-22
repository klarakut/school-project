package com.gfa.users.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.models.User;
import com.gfa.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
        value = UserRestController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserRestControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean UserService userService;
  @MockBean UserDetailsService userDetailsService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void can_register() throws Exception {

    Mockito.when(userService.store(Mockito.any()))
            .thenReturn(new UserResponseDto(new User("username", "email@domain.com", "password"),"x"));

    mvc.perform(
                    MockMvcRequestBuilders.post("/register")
                            .content(
                                    "{\"username\": \"username\", \"email\": \"email@domain.com\", \"password\": \"password\"}")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username", is("username")))
            .andExpect(jsonPath("$.email", is("email@domain.com")));
  }
}
