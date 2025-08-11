package org.generations.restfuldemodyd.mappers;

import org.generations.restfuldemodyd.dtos.PlayerDTO;
import org.generations.restfuldemodyd.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    //Exponemos al cliente, esto al uso es un objeto construido de tipo characterDTO
    //Es como si tuvieramos un CharacterDTO dto;
    public PlayerDTO playerDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setRace(player.getRace());
        dto.setLevel(player.getLevel());
        return  dto;
    }

    public Player player(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setId(playerDTO.getId());
        player.setName(playerDTO.getName());
        player.setRace(playerDTO.getRace());
        player.setLevel(playerDTO.getLevel());
        return player;
    }
}
