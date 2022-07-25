package com.gfa.users.controllers;

import com.gfa.common.dtos.ResponseDto;

import com.gfa.users.dtos.PermissionCreateRequestDto;
import com.gfa.users.dtos.PermissionErrorResponseDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.dtos.PermissionPatchRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PermissionExistsException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.models.Permission;
import com.gfa.users.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

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
      return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("ability is required"), HttpStatus.valueOf(400));
    } catch (PermissionExistsException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Ability already exist"), HttpStatus.valueOf(409));
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow error"), HttpStatus.valueOf(410));
    }
  }

  @GetMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable Long id) {
    try {
      PermissionResponseDto dtoResponse = permissionService.show(id);
      return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid id"), HttpStatus.valueOf(400));
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Permission not found"), HttpStatus.valueOf(404));
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow erroe"), HttpStatus.valueOf(410));
    }
  }

  @PatchMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> update(
      @PathVariable Long id, @RequestBody PermissionPatchRequestDto permissionPatchRequestDto) {
    try {
      PermissionResponseDto dtoResponse = permissionService.update(id, permissionPatchRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid id"), HttpStatus.valueOf(400));
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Permission not found"), HttpStatus.valueOf(404));
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid data"), HttpStatus.valueOf(400));
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow error"), HttpStatus.valueOf(410));
    }
  }

  @DeleteMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable Long id) {
    try {
      EmptyResponseDto dtoResponse = permissionService.destroy(id);
      return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Invalid id"), HttpStatus.valueOf(400));
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Permission not found"), HttpStatus.valueOf(404));
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(
          new PermissionErrorResponseDto("Unknow error"), HttpStatus.valueOf(410));
    }
  }
}
