package com.bettingapp.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BetslipBetDTO {

    private String homeTeam;
    private String awayTeam;
    private String outcome;
    private double oddValue;
}