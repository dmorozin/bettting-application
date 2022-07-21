package com.bettingapp.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerBetslipsDTO {

    private long betslipId;
    private String createdOn;
    private double stake;
    private double gain;
}
