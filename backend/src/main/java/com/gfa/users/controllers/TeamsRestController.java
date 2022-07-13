package com.gfa.users.controllers;

import com.gfa.common.dtos.*;
import com.gfa.users.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class TeamsRestController {

  public final TeamService teamService;

  @Autowired
  public TeamsRestController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping("/teams")
  public List<TeamResponseDto> index() {
    return teamService.index();
  }

  @PostMapping("/teams")
  public ResponseEntity<? extends ResponseDto> store(
      @RequestBody TeamCreateRequestDto teamCreateRequestDto) {

    try {
      TeamResponseDto dtoRespone = teamService.store(teamCreateRequestDto);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(
          new TeamErrorResponseDto("Team is required"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamExsistException e) {
      return new ResponseEntity<>(
          new TeamErrorResponseDto("Team is alredy exist"), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/teams/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable Long id) {

    try {
      TeamResponseDto dtoRespone = teamService.show(id);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidEmptyException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamNotFoundException e) {
      return new ResponseEntity<>(
          new TeamErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/teams/{id}")
  public ResponseEntity<? extends ResponseDto> update(
      @PathVariable Long id, @RequestBody TeamPatchRequestDto teamPatchRequestDto) {
    try {
      TeamResponseDto dtoRespone = teamService.update(id, teamPatchRequestDto);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamNotFoundException e) {
      return new ResponseEntity<>(
          new TeamErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidRequestException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid data"), HttpStatus.BAD_REQUEST);
    }catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/teams/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable Long id) {
    try {
      EmptyResponseDto dtoRespone =  teamService.destroy(id);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamNotFoundException e) {
      return new ResponseEntity<>(
          new TeamErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/teams/{id}/user")
  public ResponseEntity<? extends ResponseDto> addUserToTeam(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto){
    try {
      StatusResponseDto dtoRespone = teamService.addUserToTeam(id, userRequestDto);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidEmptyException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamNotFoundException e) {
      return new ResponseEntity<>(
              new TeamErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }


  @DeleteMapping("/teams/{id}/user/{user_id}")
  public ResponseEntity<? extends ResponseDto> deleteUserFromTeam(@PathVariable Long id, @PathVariable Long user_id ){
    try {
      EmptyResponseDto dtoRespone = teamService.deleteUserFromTeam(id, user_id);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
      //
    } catch (InvalidEmptyException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamAndUserNotFoundException e) {
      return new ResponseEntity<>(
              new TeamErrorResponseDto("Team or User not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }


  @PostMapping("/teams/{id}/permissions")
  public ResponseEntity<? extends ResponseDto> addPermissionsToTeam(@PathVariable Long id, @RequestBody PermissionRequestDto permissionRequestDto){

    try {
      StatusResponseDto dtoRespone = teamService.addPermissionsToTeam(id, permissionRequestDto);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidEmptyException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamNotFoundException e) {
      return new ResponseEntity<>(
              new TeamErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/teams/{id}/permissions/{permission_id}")
  public ResponseEntity<? extends ResponseDto> deletePermissionFromTeam(@PathVariable Long id, @PathVariable Long permission_id ){

    try {
      EmptyResponseDto dtoRespone = teamService.deletePermissionFromTeam(id, permission_id);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);

    } catch (InvalidEmptyException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamAndPermissionNotFoundException e) {
      return new ResponseEntity<>(
              new TeamErrorResponseDto("Team or Permission not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/teams/{id}/roles")
  public ResponseEntity<? extends ResponseDto> addRoleToTeam(@PathVariable Long id, @RequestBody RoleRequestDto roleRequestDto){

    try {
      StatusResponseDto dtoRespone  = teamService.addRoleToTeam(id, roleRequestDto);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidEmptyException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamNotFoundException e) {
      return new ResponseEntity<>(
              new TeamErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/teams/{id}/roles/{role_id}")
  public ResponseEntity<? extends ResponseDto> deleteRoleFromTeam(@PathVariable Long id, @PathVariable Long role_id ){
    EmptyResponseDto dtoRespone = teamService.deleteRoleFromTeam(id, role_id);                                                                               // ????????
    return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    try {
      teamService.deleteRoleFromTeam(id, role_id);
      return null;
      //
    } catch (InvalidEmptyException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamAndRoleNotFoundException e) {
      return new ResponseEntity<>(
              new TeamErrorResponseDto("Team or Role not found"), HttpStatus.BAD_REQUEST);
    } catch (InvalidServerError e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Server error"), HttpStatus.BAD_REQUEST);
    }
  }


}
