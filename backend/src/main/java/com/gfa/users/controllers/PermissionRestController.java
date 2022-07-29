package com.gfa.users.controllers;


import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.dtos.PermissionCreateRequestDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.dtos.PermissionPatchRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;
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

    PermissionResponseDto dtoResponse = permissionService.store(permissionCreateRequestDto);
    return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
  }

  @GetMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable Long id) {

    PermissionResponseDto dtoResponse = permissionService.show(id);
    return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
  }

  @PatchMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> update(
      @PathVariable Long id, @RequestBody PermissionPatchRequestDto permissionPatchRequestDto) {

    PermissionResponseDto dtoResponse = permissionService.update(id, permissionPatchRequestDto);
    return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
  }

  @DeleteMapping("/permissions/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable Long id) {

    EmptyResponseDto dtoResponse = permissionService.destroy(id);
    return new ResponseEntity<>(dtoResponse, HttpStatus.valueOf(200));
  }
}
