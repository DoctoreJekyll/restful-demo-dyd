package org.generations.restfuldemodyd.mappers;

import org.generations.restfuldemodyd.dtos.PlayerDTO;
import org.generations.restfuldemodyd.errors.ResourceNotFoundException;
import org.generations.restfuldemodyd.model.Job;
import org.generations.restfuldemodyd.model.Player;
import org.generations.restfuldemodyd.repository.JobRepository;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    private final JobRepository jobRepository;

    public PlayerMapper(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    //Exponemos al cliente, esto al uso es un objeto construido de tipo characterDTO
    //Es como si tuvieramos un CharacterDTO dto;
    public PlayerDTO mapToDTO (Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setRace(player.getRace());
        dto.setLevel(player.getLevel());
        dto.setJobId(player.getJob().getId());
        return  dto;
    }

    public Player mapToEntity(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setId(playerDTO.getId());
        player.setName(playerDTO.getName());
        player.setRace(playerDTO.getRace());
        player.setLevel(playerDTO.getLevel());

        Job job = jobRepository.findById(playerDTO.getJobId()).orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        player.setJob(job);
        return player;
    }
}
