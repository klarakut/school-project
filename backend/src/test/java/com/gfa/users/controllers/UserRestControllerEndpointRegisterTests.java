package com.gfa.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.users.exceptions.InvalidEmailException;
import com.gfa.users.dtos.UserCreateRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.ShortUsernameException;
import com.gfa.users.exceptions.UsernameMissingException;
import com.gfa.users.exceptions.UsernameTakenException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import com.gfa.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    value = UserRestController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration
@AutoConfigureMockMvc
class UserRestControllerEndpointRegisterTests {

  @Autowired private MockMvc mvc;

  @MockBean private UserService userService;
  @MockBean private UserDetailsService userDetailsService;
  @MockBean private UserRepository userRepository;
  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void can_register_new_user_test() throws Exception {
    UserCreateRequestDto request = new UserCreateRequestDto("johny", "john@gmail.com", "123456789");
    User user = new User(request, 10L, "");
    UserResponseDto response = new UserResponseDto(user, "");

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(request);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(201));
  }

  @Test
  void throws_exception_if_the_username_is_empty() throws Exception {
    UserCreateRequestDto user = new UserCreateRequestDto("", "john@gmail.com", "123456789");
    Mockito.when(userService.store(Mockito.any())).thenThrow(UsernameMissingException.class);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void endpoint_throws_exception_if_the_password_is_empty() throws Exception {
    UserCreateRequestDto user = new UserCreateRequestDto("john", "john@gmail.com", "");
    Mockito.when(userService.store(Mockito.any())).thenThrow(EmailMissingException.class);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void endpoint_throws_exception_if_the_email_is_empty() throws Exception {
    UserCreateRequestDto user = new UserCreateRequestDto("john", "", "123456789");
    Mockito.when(userService.store(Mockito.any())).thenThrow(EmailMissingException.class);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void endpoint_throws_exception_if_the_email_is_invalid() throws Exception {
    UserCreateRequestDto user = new UserCreateRequestDto("johny", "johngmail.com", "123456789");
    Mockito.when(userService.store(Mockito.any())).thenThrow(InvalidEmailException.class);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void endpoint_throws_exception_if_the_username_is_already_taken() throws Exception {
    UserCreateRequestDto user = new UserCreateRequestDto("johny", "john@gmail.com", "123456789");
    Mockito.when(userService.store(Mockito.any())).thenThrow(UsernameTakenException.class);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(409));
  }

  @Test
  void endpoint_throws_exception_if_the_username_is_short() throws Exception {
    UserCreateRequestDto user = new UserCreateRequestDto("jo", "john@gmail.com", "123456789");
    Mockito.when(userService.store(Mockito.any())).thenThrow(ShortUsernameException.class);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void endpoint_throws_exception_if_the_password_is_short() throws Exception {
    UserCreateRequestDto user = new UserCreateRequestDto("johny", "john@gmail.com", "1234");
    Mockito.when(userService.store(Mockito.any())).thenThrow(ShortPasswordException.class);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    mvc.perform(
            MockMvcRequestBuilders.post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }
}
