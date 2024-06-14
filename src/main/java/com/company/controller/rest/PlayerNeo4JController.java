package com.company.controller.rest;

import com.company.model.CreatePlayerRequest;
import com.company.node.PlayerNode;
import com.company.service.PlayerServiceImpl;
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
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
@Slf4j
public class PlayerNeo4JController {
    private final PlayerServiceImpl playerService;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PlayerNode> createPlayer(
            @Valid @RequestBody CreatePlayerRequest request
    ) {
        log.info("PlayerNeo4JController::createPlayer");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.createPlayer(request));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PlayerNode>> getAllPlayers() {
        log.info("PlayerNeo4JController::getAllPlayers");
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.getAllPlayers());
    }

    @GetMapping(value = "/{playerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PlayerNode> getPlayerById(@PathVariable UUID playerId) {
        log.info("PlayerNeo4JController::getPlayerById");
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.getPlayerById(playerId));
    }
}
