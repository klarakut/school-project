package com.gfa.users.controllers;

import com.gfa.common.dtos.RoleResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@WebMvcTest(RoleRestController.class)
class RoleRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleRestController roleRestController;

    @Test
    void index() throws Exception {

        List<RoleResponseDto> roles = Arrays.asList(new RoleResponseDto(1L,"HR"),new RoleResponseDto(2L,"PR"));
        Mockito.when(roleRestController.index()).thenReturn(roles);

        mvc.perform(MockMvcRequestBuilders.get("/roles"));
                //.andExpect(jsonPath("$[1].role",is("PR")));
    }

    @Test
    void store() {
    }

    @Test
    void show() {
    }

    @Test
    void update() {
    }

    @Test
    void destroy() {
    }

    @Test
    void storePermission() {
    }

    @Test
    void destroyPermission() {
    }
}