package com.bettingapp.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PlayerBetslipsDTO {

    private long betslipId;
    private LocalDateTime createdOn;
    private List<PlacedBetsDTO> placedBets;
    private double stake;
    private double gain;

    @Getter
    @AllArgsConstructor
    public static class PlacedBetsDTO {

        private String homeTeam;
        private String awayTeam;
        private String outcome;
        private double odd;
    }
}
