package com.company.controller.rest;

import com.company.model.CreateTeamRequest;
import com.company.node.TeamNode;
import com.company.service.TeamServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamNeo4JController {
    private final TeamServiceImpl teamService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TeamNode> createTeam(@Valid @RequestBody CreateTeamRequest request) {
        log.info("TeamNeo4JController::createTeam");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.createTeam(request));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TeamNode>> getAllTeams() {
        log.info("TeamNeo4JController::getAllTeams");
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.getAllTeams());
    }

    @GetMapping(value = "/{teamId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TeamNode> getTeamByTeamId(@PathVariable final UUID teamId) {
        log.info("TeamNeo4JController::getTeamByTeamId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.getTeamByTeamId(teamId));
    }
}
