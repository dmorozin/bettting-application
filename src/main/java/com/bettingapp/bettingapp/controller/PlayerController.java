package com.bettingapp.bettingapp.controller;

import com.bettingapp.bettingapp.dto.BetInsertionDTO;
import com.bettingapp.bettingapp.dto.BetslipBetDTO;
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

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerInfoDTO> getPlayerInfo(@PathVariable long playerId) {

        return ResponseEntity.ok(playerService.getPlayerInfo(playerId));
    }

    @GetMapping("/{playerId}/betslip")
    public ResponseEntity<List<PlayerBetslipsDTO>> getPlayerBets(@PathVariable long playerId) {

        return ResponseEntity.ok(playerService.getPlayerBets(playerId));
    }

    @GetMapping("/betslip/{betslipId}")
    public ResponseEntity<List<BetslipBetDTO>> getBetslipBets(@PathVariable long betslipId) {

        return ResponseEntity.ok(playerService.getBetlipBets(betslipId));
    }

    @PostMapping("/{playerId}/betslip")
    public ResponseEntity<Long> addNewBetslip(@PathVariable long playerId,
                                              @RequestBody BetInsertionDTO betInsertionDTO) {

        return ResponseEntity.ok(playerService.addNewBetslip(playerId, betInsertionDTO));
    }
}
