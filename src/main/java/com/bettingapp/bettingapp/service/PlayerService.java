package com.bettingapp.bettingapp.service;

import com.bettingapp.bettingapp.dto.BetInsertionDTO;
import com.bettingapp.bettingapp.dto.PlayerInfoDTO;

public interface PlayerService {

    PlayerInfoDTO getPlayerInfo(long playerId);

    long addNewBetslip(long playerId, BetInsertionDTO betInsertionDTO);
}
