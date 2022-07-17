package com.bettingapp.bettingapp.service.impl;

import com.bettingapp.bettingapp.dto.OddDTO;
import com.bettingapp.bettingapp.dto.OfferDTO;
import com.bettingapp.bettingapp.model.Offer;
import com.bettingapp.bettingapp.repository.OfferRepository;
import com.bettingapp.bettingapp.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Page<OfferDTO> getOffers(Pageable pageable) {

        Page<Offer> offers = offerRepository.findAll(pageable);
        return offers.map(this::toDto);
    }

    private OfferDTO toDto(Offer offer) {

        return new OfferDTO(offer.getId(),
                offer.getHomeTeam(),
                offer.getAwayTeam(),
                offer.getOdds().stream()
                        .map(odd -> new OddDTO(odd.getId(), odd.getOutcome().getTitle(), odd.getValue()))
                        .collect(Collectors.toList()));
    }

}
