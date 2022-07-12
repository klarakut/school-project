package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.models.Team;
import com.gfa.users.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseTeamService implements TeamService {
  public final TeamRepository teamRepository;

  @Autowired
  public DatabaseTeamService(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }


  @Override
  public List<TeamResponseDto> index() {
    List<Team> findAllTeam = teamRepository.findAll();
    List<TeamResponseDto> findAllTeamResponse = new ArrayList<>();

    for (Team team : findAllTeam) {
      TeamResponseDto response = new TeamResponseDto(team.getId(),team.getName());
      findAllTeamResponse.add(response);
    }
    return findAllTeamResponse;
  }

  @Override
  public TeamResponseDto store(TeamCreateRequestDto teamCreateRequestDto) {
    Team team = new Team(teamCreateRequestDto.getName());

    if(teamCreateRequestDto.getName().isEmpty()){
      throw new InvalidRequestException();
    }
    if (!teamCreateRequestDto.getName().equals(team.getName())){
      throw new InvalidTeamExsistException();
    }
      teamRepository.save(team);
      return new TeamResponseDto(team.getId(), team.getName());
  }

  @Override
  public TeamResponseDto show(Long id) {
    Team  team = teamRepository.findById(id).get();

    if (id <= 0){
      throw new InvalidIdException();
    }
    if (!id.equals(team.getId())){
      throw new InvalidTeamNotFoundException();
    }
      return new TeamResponseDto(team.getId(),team.getName());

  }

  @Override
  public TeamResponseDto update(Long id, TeamPatchRequestDto teamPatchRequestDto) {
    Team  team =  teamRepository.findById(id).get();
    if (id <= 0){
      throw new InvalidIdException();
    }
    if (!id.equals(team.getId())){
      throw new InvalidTeamNotFoundException();
    }
    if (teamPatchRequestDto.getName().isEmpty()){
      throw new InvalidRequestException();
    }
      team.setName(teamPatchRequestDto.getName());
      teamRepository.save(team);
      return new TeamResponseDto(team.getId(), team.getName());
  }

  @Override
  public TeamResponseDto destroy(Long id) {
    Team  team =  teamRepository.findById(id).get();

    if (id <= 0){
      throw new InvalidIdException();
    }
    if (!id.equals(team.getId())){
      throw new InvalidTeamNotFoundException();
    }
      teamRepository.delete(team);
      teamRepository.save(team);
      return new TeamResponseDto(team.getId(),team.getName());      ////?????????
  }


}
