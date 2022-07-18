package com.bettingapp.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OddDTO {

    private long oddId;
    private String outcome;
    private double oddValue;
}
