package com.gfa.users.services;

import com.gfa.common.dtos.TeamCreateRequestDto;
import com.gfa.common.dtos.TeamPatchRequestDto;
import com.gfa.common.dtos.TeamRequestDto;
import com.gfa.common.dtos.TeamResponseDto;

import java.util.List;

public interface TeamService {

    public List<TeamResponseDto> index ();

    public TeamResponseDto  store (TeamCreateRequestDto teamCreateRequestDto);

    public TeamResponseDto show(Long id);

    public TeamResponseDto update (Long id, TeamPatchRequestDto teamPatchRequestDto);

    public TeamResponseDto destroy(Long id);
}
