package com.gfa.users.services;

import com.gfa.common.dtos.RoleCreateRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
import com.gfa.users.models.Role;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseRoleServiceTest {


    @Test
    void index() {
        //Arrange
        RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
        PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);

        List<Role> roles = Arrays.asList(new Role("HR"),new Role("PR"));
        Mockito.when(mockedRoleRepo.findAll()).thenReturn(roles);

        RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
        // Act
        List<RoleResponseDto> result = roleService.index();
        //Assert
        assertEquals(2,result.size());
    }

    @Test
    void show() {
        //Arrange
        RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
        PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);

        List<Role> roles = Arrays.asList(new Role("HR"),new Role("PR"));
        Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(roles.stream().findFirst());

        RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
        // Act
        RoleResponseDto result = roleService.show(1L);
        //Assert
        assertEquals("HR",result.role);
    }

    @Test
    void store() {
        //Arrange
        RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
        PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);

        List<Role> roles = Arrays.asList(new Role("HR"),new Role("PR"));
        RoleCreateRequestDto dto = new RoleCreateRequestDto("SHIFT RUNNER");
        Role role = new Role(dto.role);

        //Mockito.when(mockedRoleRepo.save(role)).thenReturn(roles.add(role.getRole()));

        RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

        // Act
        RoleResponseDto result = roleService.store(dto);

        //Assert
        assertEquals(3,result.id);
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