package com.company.service;

import com.company.exception.TeamNotFoundException;
import com.company.model.CreateTeamRequest;
import com.company.node.TeamNode;
import com.company.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl {
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public TeamNode createTeam(CreateTeamRequest request) {
        log.info("TeamServiceImpl::createTeam");
        TeamNode newTeam = modelMapper.map(request, TeamNode.class);
        return teamRepository.save(newTeam);
    }

    public List<TeamNode> getAllTeams() {
        log.info("TeamServiceImpl::getAllTeams");
        return teamRepository.findAll();
    }

    public TeamNode getTeamByTeamId(final UUID teamId) {
        log.info("TeamServiceImpl::getTeamByTeamId");
        return teamRepository.findById(teamId)
                .orElseThrow(() -> {
                    return new TeamNotFoundException("no team found with teamId: " + teamId);
                });
    }
}
