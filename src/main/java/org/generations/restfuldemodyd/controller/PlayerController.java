package org.generations.restfuldemodyd.controller;

import org.generations.restfuldemodyd.dtos.PlayerDTO;
import org.generations.restfuldemodyd.errors.ResourceNotFoundException;
import org.generations.restfuldemodyd.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping()
    public ResponseEntity<List<PlayerDTO>> getAll() {
        List<PlayerDTO>  playerDTOS = playerService.findAll();
        return new ResponseEntity<>(playerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getById(@PathVariable Integer id) {
        PlayerDTO playerDTO = playerService.findById(id);
        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    @GetMapping("/job/name/{jobName}")
    public ResponseEntity<Page<PlayerDTO>> listByJobName(@PathVariable String jobName, @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<PlayerDTO> playerDTOS = playerService.findByJobIgnoreCaseContaining(jobName, pageable);
        return ResponseEntity.ok(playerDTOS);
    }

    @GetMapping("/job/id/{jobId}")
    public ResponseEntity<Page<PlayerDTO>> listByJobId(@PathVariable Integer jobId, Pageable pageable) {
        Page<PlayerDTO> playerDTOS = playerService.findByJobId(jobId, pageable);
        return ResponseEntity.ok(playerDTOS);
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO playerDTO1 = playerService.save(playerDTO);
        return new ResponseEntity<>(playerDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable Integer id, @RequestBody PlayerDTO playerDTO) {
        if (!playerService.existById(id)) {
            throw new ResourceNotFoundException("Player not found");
        }
        playerDTO.setId(id);
        PlayerDTO updatedPlayer = playerService.save(playerDTO);
        return ResponseEntity.ok(updatedPlayer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PlayerDTO> delete(@PathVariable Integer id) {
        playerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
