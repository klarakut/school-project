package com.gfa.users.controllers;

import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.users.dtos.TeamResponseDto;
import com.gfa.users.dtos.TeamCreateRequestDto;
import com.gfa.users.dtos.TeamPatchRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.dtos.UserRequestDto;
import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.dtos.RoleRequestDto;
import com.gfa.common.dtos.ResponseDto;

import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.TeamExistsException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.TeamNotFoundException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.exceptions.UserExistsException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.exceptions.PermissionExistsException;
import com.gfa.users.exceptions.RoleNotFoundException;
import com.gfa.users.exceptions.RoleExistsException;

import com.gfa.users.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
public class TeamsRestController {

  public final TeamService teamService;

  @Autowired
  public TeamsRestController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping("/teams")
  public ResponseEntity<List<TeamResponseDto>> index() {
    return new ResponseEntity(teamService.index(), HttpStatus.OK);
  }

  @PostMapping("/teams")
  public ResponseEntity<? extends ResponseDto> store(
      @RequestBody TeamCreateRequestDto teamCreateRequestDto) {

    try {
      TeamResponseDto dtoResponse = teamService.store(teamCreateRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team is required"), HttpStatus.BAD_REQUEST);
    } catch (TeamExistsException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team is alredy exist"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/teams/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable Long id) {

    try {
      TeamResponseDto dtoResponse = teamService.show(id);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/teams/{id}")
  public ResponseEntity<? extends ResponseDto> update(
      @PathVariable Long id, @RequestBody TeamPatchRequestDto teamPatchRequestDto) {
    try {
      TeamResponseDto dtoResponse = teamService.update(id, teamPatchRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid data"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/teams/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable Long id) {
    try {
      EmptyResponseDto dtoResponse = teamService.destroy(id);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/teams/{id}/user")
  public ResponseEntity<? extends ResponseDto> addUserToTeam(
      @PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
    try {
      StatusResponseDto dtoResponse = teamService.addUserToTeam(id, userRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("User not found"), HttpStatus.BAD_REQUEST);
    } catch (UserExistsException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("User already exist in team"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/teams/{id}/user/{userId}")
  public ResponseEntity<? extends ResponseDto> deleteUserFromTeam(
      @PathVariable Long id, @PathVariable Long userId) {
    try {
      EmptyResponseDto dtoResponse = teamService.deleteUserFromTeam(id, userId);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
      //
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("User not found"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/teams/{id}/permissions")
  public ResponseEntity<? extends ResponseDto> addPermissionsToTeam(
      @PathVariable Long id, @RequestBody PermissionRequestDto permissionRequestDto) {

    try {
      StatusResponseDto dtoResponse = teamService.addPermissionsToTeam(id, permissionRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    } catch (PermissionExistsException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Permission already exist"), HttpStatus.BAD_REQUEST);
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Permission not exist"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/teams/{id}/permissions/{permissionId}")
  public ResponseEntity<? extends ResponseDto> deletePermissionFromTeam(
      @PathVariable Long id, @PathVariable Long permissionId) {

    try {
      EmptyResponseDto dtoResponse = teamService.deletePermissionFromTeam(id, permissionId);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);

    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (PermissionNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Permission not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/teams/{id}/roles")
  public ResponseEntity<? extends ResponseDto> addRoleToTeam(
      @PathVariable Long id, @RequestBody RoleRequestDto roleRequestDto) {

    try {
      StatusResponseDto dtoResponse = teamService.addRoleToTeam(id, roleRequestDto);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (RoleNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Role not found"), HttpStatus.BAD_REQUEST);
    } catch (RoleExistsException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Role exist in team"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/teams/{id}/roles/{roleId}")
  public ResponseEntity<? extends ResponseDto> deleteRoleFromTeam(
      @PathVariable Long id, @PathVariable Long roleId) {

    try {
      EmptyResponseDto dtoResponse = teamService.deleteRoleFromTeam(id, roleId);
      return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
      //
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (TeamNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (RoleNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Role not found"), HttpStatus.BAD_REQUEST);
    } catch (UnknownErrorException e) {
      return new ResponseEntity<>(new ErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }
}