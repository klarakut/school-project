package com.gfa.users.controllers;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.UserResponseDto;
import com.gfa.users.models.User;
import com.gfa.users.services.UserService;
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


    @Test
    void register_new_user_test() throws Exception{
        CreateUserRequestDto u = new CreateUserRequestDto("john","john@gmail.com","1234");
        User user = new User(u, Long.valueOf(60),"xxx");
        UserResponseDto response = new UserResponseDto(user,"xy");
        Mockito.when(userService.store(u)).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.post("/register"))
                        //.content("{\"username\":\"john\",\"email\":\"john@mail.com\",\"password\":\"1234\"}")
                        //.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
                //.andExpect(jsonPath("$[0].username").value("john"));
                //.andExpect(jsonPath(response.username).value("john"));
                //.andExpect(jsonPath("email",is("john@gmail.com")));
                //.andExpect(response.username,is("john"));
        //assertEquals;
    }

    /*@Test
    void post_register_new_user_test() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/register")
                        .content("{\"username\":\"john\",\"email\":\"john@mail.com\",\"password\":\"1234\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].status").value("ok"));
    }*/
}