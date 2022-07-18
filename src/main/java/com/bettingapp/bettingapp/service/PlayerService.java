package com.bettingapp.bettingapp.service;

import com.bettingapp.bettingapp.dto.BetInsertionDTO;
import com.bettingapp.bettingapp.dto.PlayerBetslipsDTO;
import com.bettingapp.bettingapp.dto.PlayerInfoDTO;

import java.util.List;

public interface PlayerService {

    PlayerInfoDTO getPlayerInfo(long playerId);

    long addNewBetslip(long playerId, BetInsertionDTO betInsertionDTO);

    List<PlayerBetslipsDTO> getPlayerBets(long playerId);
}
