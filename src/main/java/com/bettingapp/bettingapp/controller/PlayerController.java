package com.bettingapp.bettingapp.controller;

import com.bettingapp.bettingapp.dto.BetInsertionDTO;
import com.bettingapp.bettingapp.dto.PlayerBetslipsDTO;
import com.bettingapp.bettingapp.dto.PlayerInfoDTO;
import com.bettingapp.bettingapp.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {

        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerInfoDTO> getPlayerInfo(@PathVariable long id) {

        return ResponseEntity.ok(playerService.getPlayerInfo(id));
    }

    @GetMapping("/{id}/betslip")
    public ResponseEntity<List<PlayerBetslipsDTO>> getPlayerBets(@PathVariable long id) {

        return ResponseEntity.ok(playerService.getPlayerBets(id));
    }


    @PostMapping("/{id}/betslip")
    public ResponseEntity<Long> addNewBetslip(@PathVariable long id,
                                              @RequestBody BetInsertionDTO betInsertionDTO) {

        return ResponseEntity.ok(playerService.addNewBetslip(id, betInsertionDTO));
    }
}
