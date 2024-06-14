package com.company.service;

import com.company.exception.PlayerNotFoundException;
import com.company.model.CreatePlayerRequest;
import com.company.node.PlayerNode;
import com.company.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl {
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public PlayerNode createPlayer(CreatePlayerRequest request) {
        log.info("PlayerServiceImpl::createPlayer");
        PlayerNode playerNode = modelMapper.map(request, PlayerNode.class);
        return playerRepository.save(playerNode);
    }

    public List<PlayerNode> getAllPlayers() {
        log.info("PlayerServiceImpl::getAllPlayers");
        return playerRepository.findAll();
    }

    public PlayerNode getPlayerById(final UUID playerId) {
        log.info("PlayerServiceImpl::getPlayerById");
        return playerRepository.findById(playerId).orElseThrow(() -> {
            return new PlayerNotFoundException("no player found with id: " + playerId);
        });
    }
}
