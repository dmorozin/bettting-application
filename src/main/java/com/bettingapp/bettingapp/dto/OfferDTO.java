package com.bettingapp.bettingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OfferDTO {

    private long offerId;
    private String homeTeam;
    private String awayTeam;
    private List<OddDTO> odds;
}
