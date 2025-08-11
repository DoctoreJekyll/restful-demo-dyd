package org.generations.restfuldemodyd.controller;

import org.generations.restfuldemodyd.dtos.PlayerDTO;
import org.generations.restfuldemodyd.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAll() {
        List<PlayerDTO>  playerDTOS = playerService.findAll();
        return new ResponseEntity<>(playerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getById(@PathVariable Integer id) {
        PlayerDTO playerDTO = playerService.findById(id);
        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO playerDTO1 = playerService.save(playerDTO);
        return new ResponseEntity<>(playerDTO1, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PlayerDTO> update(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO playerDTO1 = playerService.save(playerDTO);
        return new ResponseEntity<>(playerDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlayerDTO> delete(@PathVariable Integer id) {
        playerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
