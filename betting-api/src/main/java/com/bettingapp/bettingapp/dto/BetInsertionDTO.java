package com.bettingapp.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BetInsertionDTO {

    private double stake;
    private double gain;
    private List<BetDTO> bets;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BetDTO {

        private long offerId;
        private long oddId;
    }
}
