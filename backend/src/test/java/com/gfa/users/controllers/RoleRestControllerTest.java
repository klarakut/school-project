package com.gfa.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.common.dtos.RoleCreateRequestDto;
import com.gfa.users.models.Role;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.services.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = RoleRestController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration

//@WebMvcTest(RoleRestController.class)
//@SpringBootTest(classes = RoleRestController.class)
@AutoConfigureMockMvc
class RoleRestControllerTest {

    @Autowired
    private MockMvc mvc;

    //@MockBean
    @Autowired
    private RoleRestController roleRestController;
    /*@MockBean
    private PermissionRepository permissionRepository;*/

    @MockBean
    //@Autowired
    private RoleRepository roleRepository;
    @MockBean
    //@Autowired
    private RoleService roleService;

    /*@Autowired
    private WebApplicationContext webApplicationContext;*/

    /*@BeforeEach
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }*/


    @Test
    void index() throws Exception {

        //assertEquals(0,roleRepository.count());
        roleRepository.save(new Role("role #1"));
        roleRepository.save(new Role("role #1"));
        //List<Role> roles = roleRepository.findAll();
        //Mockito.when(roleRepository.findAll()).thenReturn(roles);
        assertEquals(2,roleRepository.count());



        mvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().is(200));

    }


    @Test
    void store() throws Exception{
        RoleCreateRequestDto dto = new RoleCreateRequestDto("hr");
        //RoleResponseDto response = new RoleResponseDto(1l,"x");
        //Mockito.when(roleService.store(Mockito.any())).thenThrow(EmptyBodyException.class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);
        mvc.perform(MockMvcRequestBuilders.post("/roles")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void show() throws Exception{
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