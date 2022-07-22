package com.gfa.users.controllers;

import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.dtos.*;
import com.gfa.users.exceptions.*;
import com.gfa.users.models.Permission;
import com.gfa.users.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PermissionRestController {
  public final PermissionService permissionService;

  @Autowired
  public PermissionRestController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @GetMapping("/permissions")
  public ResponseEntity<List<Permission>> index() {
    return new ResponseEntity(permissionService.index(), HttpStatus.OK);
  }

  @PostMapping("/permissions")
  public ResponseEntity<? extends ResponseDto> store(
      @RequestBody PermissionCreateRequestDto permissionCreateRequestDto) {
    try {
      PermissionResponseDto dtoResponse = permissionService.store(permissionCreateRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("ability is required"), HttpStatus.BAD_REQUEST);
    } catch (PermissionExistsException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Ability already exist"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow erroe"), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable Long id) {
    try {
      PermissionResponseDto dtoResponse = permissionService.show(id);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Permission not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow erroe"), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> update(
      @PathVariable Long id, @RequestBody PermissionPatchRequestDto permissionPatchRequestDto) {
    try {
      PermissionResponseDto dtoResponse = permissionService.update(id, permissionPatchRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Permission not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid data"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow error"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable Long id) {
    try {
      EmptyResponseDto dtoResponse = permissionService.destroy(id);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Permission not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow error"), HttpStatus.BAD_REQUEST);
    }
  }
}
