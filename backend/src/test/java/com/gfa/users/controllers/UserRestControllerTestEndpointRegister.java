package com.gfa.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.UserResponseDto;
import com.gfa.users.exceptions.*;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import com.gfa.users.services.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        value = UserRestController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void register_new_user_test() throws Exception{
        CreateUserRequestDto request = new CreateUserRequestDto("johny","john@gmail.com","123456789");
        User user = new User(request, 10L,"");
        UserResponseDto response = new UserResponseDto(user,"");
        Mockito.when(userService.store(Mockito.any())).thenReturn(response);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void register_user_empty_username() throws Exception{
        CreateUserRequestDto user = new CreateUserRequestDto("","john@gmail.com","123456789");
        Mockito.when(userService.store(Mockito.any())).thenThrow(UsernameMissingException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void register_user_empty_password() throws Exception{
        CreateUserRequestDto user = new CreateUserRequestDto("john","john@gmail.com","");
        Mockito.when(userService.store(Mockito.any())).thenThrow(EmailMissingException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void register_user_empty_email() throws Exception{
        CreateUserRequestDto user = new CreateUserRequestDto("john","","123456789");
        Mockito.when(userService.store(Mockito.any())).thenThrow(EmailMissingException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void register_user_wrong_email() throws Exception{
        CreateUserRequestDto user = new CreateUserRequestDto("johny","johngmail.com","123456789");
        Mockito.when(userService.store(Mockito.any())).thenThrow(InvalidEmailException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
    @Test
    void register_user_taken_username() throws Exception{
        //List<User> users = Arrays.asList(new User(new CreateUserRequestDto("johny","john@gmail.com","123456789"),10L,""));
        CreateUserRequestDto user = new CreateUserRequestDto("johny","john@gmail.com","123456789");
        Mockito.when(userService.store(Mockito.any())).thenThrow(UsernameTakenException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409));
    }

    @Test
    void register_user_short_username() throws Exception{
        CreateUserRequestDto user = new CreateUserRequestDto("jo","john@gmail.com","123456789");
        Mockito.when(userService.store(Mockito.any())).thenThrow(ShortUsernameException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void register_user_short_password() throws Exception{
        CreateUserRequestDto user = new CreateUserRequestDto("johny","john@gmail.com","1234");
        Mockito.when(userService.store(Mockito.any())).thenThrow(ShortPasswordException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
}