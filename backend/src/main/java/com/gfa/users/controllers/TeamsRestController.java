package com.gfa.users.controllers;

import com.gfa.common.dtos.*;
import com.gfa.users.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamsRestController {

  public final TeamService teamService;

  @Autowired
  public TeamsRestController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping("/teams")
  public ResponseEntity<? extends TeamResponseDto> index() {
    TeamResponseDto dtoRespone = teamService.index();     //?????????
    return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    teamService.index();
    return null; // ??? podla mna má byt List preto to mám červené
  }

  @PostMapping("/teams")
  public ResponseEntity<? extends TeamResponseDto> store(
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
  public ResponseEntity<? extends TeamResponseDto> show(@PathVariable Long id) {

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
  public ResponseEntity<? extends TeamResponseDto> update(
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
    }
  }

  @DeleteMapping("/teams/{id}")
  public ResponseEntity<? extends TeamResponseDto> destroy(@PathVariable Long id) {
    try {
      TeamResponseDto dtoRespone = teamService.destroy(id);
      return new ResponseEntity<>(dtoRespone, HttpStatus.OK);
    } catch (InvalidIdException e) {
      return new ResponseEntity<>(new TeamErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
    } catch (InvalidTeamNotFoundException e) {
      return new ResponseEntity<>(
          new TeamErrorResponseDto("Team not found"), HttpStatus.BAD_REQUEST);
    }
  }
}
