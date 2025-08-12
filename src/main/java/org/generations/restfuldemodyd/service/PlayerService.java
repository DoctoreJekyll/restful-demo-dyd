package org.generations.restfuldemodyd.service;

import org.generations.restfuldemodyd.dtos.PlayerDTO;
import org.generations.restfuldemodyd.errors.ResourceNotFoundException;
import org.generations.restfuldemodyd.mappers.PlayerMapper;
import org.generations.restfuldemodyd.model.Player;
import org.generations.restfuldemodyd.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;


    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public List<PlayerDTO> findAll() {
        return playerRepository.findAll()
                .stream()
                .map(playerMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public PlayerDTO findById(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        return playerMapper.mapToDTO(player);
    }

    public PlayerDTO save(PlayerDTO playerDTO) {
        Player player = playerMapper.mapToEntity(playerDTO);
        Player playerSaved = playerRepository.save(player);
        return  playerMapper.mapToDTO(playerSaved);
    }

    public void deleteById(Integer id) {
        playerRepository.deleteById(id);
    }
}
