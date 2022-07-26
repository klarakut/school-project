package com.gfa.users.services;

import com.gfa.common.dtos.RoleRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.dtos.PermissionRequestDto;
import com.gfa.common.exceptions.EmptyBodyException;
import com.gfa.users.exceptions.IdNotFoundException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidInputException;
import com.gfa.users.exceptions.PermissionIdNotFoundException;
import com.gfa.users.exceptions.DuplicateRoleException;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DatabaseRoleServiceTest {

  // INDEX method unit test
  @Test
    void index_method_returns_the_list_of_roles() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    List<Role> roles = Arrays.asList(new Role("x"),new Role("y"));
    Mockito.when(mockedRoleRepo.findAll()).thenReturn(roles);

    List<RoleResponseDto> result = roleService.index();
    assertEquals(2,result.size());
  }

  // SHOW method unit tests
  @Test
    void show_method_returns_a_specific_role_by_id() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    List<Role> roles = Arrays.asList(new Role("x"),new Role("y"));
    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(roles.stream().findFirst());

    RoleResponseDto result = roleService.show(1L);
    assertEquals("x",result.role);
  }

  @Test
    void show_method_throws_exception_due_to_a_negative_id() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    assertThrows(InvalidIdException.class,() -> roleService.show(-1L));
  }

  @Test
    void show_method_throws_exception_because_the_role_of_the_specified_id_does_not_exist() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    List<Role> roles = Arrays.asList(new Role("x"),new Role("y"));
    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    assertThrows(IdNotFoundException.class,() -> roleService.show(1L));
  }

  // STORE method unit tests
  @Test
    void can_store_new_role() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
    RoleRequestDto dto = new RoleRequestDto("x");
    Role mockedRole = Mockito.mock(Role.class);

    Mockito.when(mockedRoleRepo.save(Mockito.any())).thenReturn(mockedRole);

    RoleResponseDto result = roleService.store(dto);
    assertEquals("x",result.role);
  }

  @Test
    void store_method_throws_exception_because_parameter_role_is_empty() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
    RoleRequestDto dto = new RoleRequestDto("");

    assertThrows(EmptyBodyException.class,() -> roleService.store(dto));
  }

  @Test
    void store_method_throws_exception_because_the_role_already_exists() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
    RoleRequestDto dto = new RoleRequestDto("x");
    Role mockedRole = Mockito.mock(Role.class);

    Mockito.when(mockedRoleRepo.findByRole(Mockito.anyString())).thenReturn(Optional.of(mockedRole));

    assertThrows(DuplicateRoleException.class,() -> roleService.store(dto));
  }

  // UPDATE method unit tests
  @Test
    void role_updated_successfully() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    RoleRequestDto dto = new RoleRequestDto("x");
    Role role = new Role("y");

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

    RoleResponseDto result = roleService.update(1L,dto);
    assertEquals("x",result.role);
  }

  @Test
    void update_method_throws_exception_due_to_a_negative_id() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    RoleRequestDto mockedDto = Mockito.mock(RoleRequestDto.class);

    assertThrows(InvalidIdException.class,() -> roleService.update(-1L,mockedDto));
  }

  @Test
    void update_method_throws_exception_because_parameter_role_is_empty() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    RoleRequestDto dto = new RoleRequestDto("");

    assertThrows(InvalidInputException.class,() -> roleService.update(1L,dto));
  }

  @Test
    void update_method_throws_exception_because_the_role_of_the_specified_id_does_not_exist() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
    RoleRequestDto dto = new RoleRequestDto("x");

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    assertThrows(IdNotFoundException.class,() -> roleService.update(1L,dto));
  }

  // DESTROY method unit tests
  @Test
    void destroy_method_destroys_the_role() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
    Role mockedRole = Mockito.mock(Role.class);

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedRole));

    StatusResponseDto result = roleService.destroy(1L);
    assertEquals("ok",result.status);
  }

  @Test
    void destroy_method_throws_exception_due_a_negative_id() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    assertThrows(InvalidIdException.class,() -> {
      roleService.destroy(-1L);
    });
  }

  @Test
    void destroy_method_throws_exception_because_the_role_with_specified_id_does_not_exist() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    assertThrows(IdNotFoundException.class,() -> {
      roleService.destroy(7L);
    });
  }

  // STORE PERMISSION method unit tests
  @Test
    void store_permission_returns_status_ok() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    Role role = Mockito.mock(Role.class);
    Permission permission = Mockito.mock(Permission.class);
    PermissionRequestDto permissionDto = new PermissionRequestDto(1L,"xy");

    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(permission));
    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

    StatusResponseDto result = roleService.storePermission(1L,permissionDto);
    assertEquals("ok",result.status);
  }

  @Test
    void store_permission_throws_exception_due_to_the_non_existent_permission() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    Role mockedRole = Mockito.mock(Role.class);
    PermissionRequestDto dto = new PermissionRequestDto(2L,"x");

    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedRole));

    assertThrows(PermissionIdNotFoundException.class,() -> {
      roleService.storePermission(1L,dto);
    });
  }

  @Test
    void store_permission_throws_exception_due_to_the_non_existent_role() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    Permission mockedPermission = Mockito.mock(Permission.class);
    PermissionRequestDto dto = new PermissionRequestDto(2L,"x");

    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedPermission));
    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    assertThrows(IdNotFoundException.class,() -> {
      roleService.storePermission(1L,dto);
    });
  }

  @Test
    void store_permission_method_throws_exception_due_to_the_empty_parameter() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    PermissionRequestDto dto = new PermissionRequestDto(1L,"");

    assertThrows(InvalidInputException.class,() -> {
      roleService.storePermission(1L,dto);
    });
  }

  @Test
    void store_permission_throws_exception_due_to_the_existent_permission() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    Permission mockedPermission = Mockito.mock(Permission.class);
    Role mockedRole = Mockito.mock(Role.class);
    PermissionRequestDto dto = new PermissionRequestDto(2L,"x");

    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedPermission));
    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedRole));
    Mockito.when(mockedRole.can((Permission) Mockito.any())).thenReturn(true);

    assertThrows(InvalidInputException.class,() -> {
      roleService.storePermission(1L,dto);
    });
  }

  // DESTROY PERMISSION method unit tests
  @Test
    void can_destroy_permission_from_list() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
    Role mockedRole = Mockito.mock(Role.class);
    Permission mockedPermission = Mockito.mock(Permission.class);

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedRole));
    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedPermission));
    Mockito.when(mockedRole.can((Permission) Mockito.any())).thenReturn(true);

    StatusResponseDto result = roleService.destroyPermission(1L,2L);
    assertEquals("ok",result.status);
  }

  @Test
    void destroy_permission_throws_exception_due_to_the_non_existent_permission() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);
    Role mockedRole = Mockito.mock(Role.class);

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedRole));
    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    assertThrows(PermissionIdNotFoundException.class,() -> {
      roleService.destroyPermission(1L,2L);
    });
  }

  @Test
    void destroy_permission_throws_exception_due_to_the_non_existent_role() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    Permission mockedPermission = Mockito.mock(Permission.class);

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedPermission));

    RoleService roleService = new DatabaseRoleService(mockedRoleRepo,mockedPermissionRepo);

    assertThrows(IdNotFoundException.class,() -> {
      roleService.destroyPermission(1L,2L);
    });
  }

  @Test
    void destroy_permission_throws_exception_because_role_does_not_have_the_permission_in_the_permissions_list() {
    RoleRepository mockedRoleRepo = Mockito.mock(RoleRepository.class);
    PermissionRepository mockedPermissionRepo = Mockito.mock(PermissionRepository.class);
    Role mockedRole = Mockito.mock(Role.class);
    Permission mockedPermission = Mockito.mock(Permission.class);

    Mockito.when(mockedRoleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedRole));
    Mockito.when(mockedPermissionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockedPermission));

    RoleService roleService = new DatabaseRoleService(mockedRoleRepo, mockedPermissionRepo);

    assertThrows(InvalidInputException.class, () -> {
      roleService.destroyPermission(1L, 2L);
    });
  }
}