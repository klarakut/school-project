package com.gfa.users.services;

import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.users.dtos.PermissionCreateRequestDto;
import com.gfa.users.dtos.PermissionPatchRequestDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.exceptions.*;
import com.gfa.users.models.Permission;
import com.gfa.users.repositories.PermissionRepository;

import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DatabasePermissionServiceTest {

  @Test
  void index() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    List<Permission> permissions =
        Arrays.asList(new Permission("create table"), new Permission("Book table"));
    Mockito.when(fakePermissionRepository.findAll()).thenReturn(permissions);
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Act
    // Assert
    assertEquals(2, permissions.size());
  }

  @Test
  void store() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionCreateRequestDto permissionCreateRequestDto =
        new PermissionCreateRequestDto("Book Table");
    Permission permission = new Permission(permissionCreateRequestDto.getAbility());
    Mockito.when(fakePermissionRepository.findByAbility(Mockito.anyString()))
        .thenReturn(Optional.empty());
    Mockito.when(fakePermissionRepository.save(Mockito.any())).thenReturn(permission);
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Act
    PermissionResponseDto result = service.store(permissionCreateRequestDto);
    // Asserts
    assertEquals("Book Table", result.ability);
  }

  @Test
  void store_emptyRequest() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionCreateRequestDto permissionCreateRequestDto = new PermissionCreateRequestDto("");
    Permission permission = new Permission(permissionCreateRequestDto.getAbility());
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Act
    // Asserts
    assertThrows(
        InvalidRequestException.class,
        () -> {
          service.store(permissionCreateRequestDto);
        });
  }

  @Test
  void store_permission_already_exists() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionCreateRequestDto permissionCreateRequestDto =
        new PermissionCreateRequestDto("Book Table");
    Permission permission = new Permission(permissionCreateRequestDto.getAbility());

    Mockito.when(fakePermissionRepository.findByAbility(Mockito.anyString()))
        .thenReturn(Optional.of(permission));
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Act
    // Asserts
    assertThrows(
        PermissionExistsException.class,
        () -> {
          service.store(permissionCreateRequestDto);
        });
  }

  @Test
  void show() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    Permission permission = new Permission("Book Table");
    Mockito.when(fakePermissionRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(permission));
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Act
    PermissionResponseDto result = service.show(1L);
    // Assert
    assertEquals("Book Table", result.ability);
  }

  @Test
  void show_negativeId() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Asser
    assertThrows(
        InvalidIdException.class,
        () -> {
          service.show(-1L);
        });
  }

  @Test
  void update() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionPatchRequestDto permissionPatchRequestDto =
        new PermissionPatchRequestDto("Vip Table book");
    Permission permission = new Permission("Book Table");
    Mockito.when(fakePermissionRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(permission));
    Mockito.when(fakePermissionRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Act
    PermissionResponseDto result = service.update(1L, permissionPatchRequestDto);
    // Assert
    assertEquals("Vip Table book", result.ability);
  }

  @Test
  void update_emptyRequest() {
    // AAA
    // Arange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionPatchRequestDto permissionPatchRequestDto = new PermissionPatchRequestDto("");
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);

    // Act
    // Assert
    assertThrows(
        InvalidRequestException.class,
        () -> {
          service.update(1L, permissionPatchRequestDto);
        });
  }

  @Test
  void update_negativeId() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionPatchRequestDto permissionPatchRequestDto =
        new PermissionPatchRequestDto("Book Table");
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Assert
    assertThrows(
        InvalidIdException.class,
        () -> {
          service.update(-1L, permissionPatchRequestDto);
        });
  }

  @Test
  void update_cannotFindPermissions() {
    //AAA
    //Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionPatchRequestDto permissionPatchRequestDto = new PermissionPatchRequestDto("Book Table");

    Mockito.when(fakePermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    //Act
    //Assert
    assertThrows(
            PermissionNotFoundException.class,
            () -> {
              service.update(1L, permissionPatchRequestDto);
            });
  }

  @Test
  void destroy() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    Permission permission = new Permission("Book Table");
    Mockito.when(fakePermissionRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(permission));
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Act
    EmptyResponseDto result = service.destroy(1L);
    // Assert
    assertEquals("ok", result.status);
  }

  @Test
  void destroy_negativeId() {
    // AAA
    // Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    // Assert
    assertThrows(
            InvalidIdException.class,
            () -> {
              service.destroy(-1L);
            });
  }

  @Test
  void destroy_cannotFindPermission() {
    //AAA
    //Arrange
    PermissionRepository fakePermissionRepository = Mockito.mock(PermissionRepository.class);
    Mockito.when(fakePermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    PermissionService service = new DatabasePermissionService(fakePermissionRepository);
    //Act
    //Assert
    assertThrows(
            PermissionNotFoundException.class,
            () -> {
              service.destroy(1L);
            });
  }
  }

