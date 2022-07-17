package com.bettingapp.bettingapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PlayerBetslipsDTO {

    private long betslipId;
    private LocalDateTime createdOn;
    private List<PlacedBetsDTO> placedBets;

    public static class PlacedBetsDTO {

        private String homeTeam;
        private String awayTeam;
        private String outcome;
        private double odd;
    }
}
