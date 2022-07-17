package com.bettingapp.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BetInsertionDTO {

    private double walletAmount;
    private List<BetDTO> bets;

    @Getter
    @AllArgsConstructor
    public static class BetDTO {

        private long offerId;
        private long oddId;
    }
}
