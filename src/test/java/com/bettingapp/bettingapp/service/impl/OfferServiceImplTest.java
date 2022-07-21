package com.bettingapp.bettingapp.service.impl;

import com.bettingapp.bettingapp.dto.OfferDTO;
import com.bettingapp.bettingapp.model.Odd;
import com.bettingapp.bettingapp.model.Offer;
import com.bettingapp.bettingapp.model.Outcome;
import com.bettingapp.bettingapp.repository.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

class OfferServiceImplTest {
    @InjectMocks
    OfferServiceImpl offerService;

    @Mock
    OfferRepository offerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOffers() {
        Outcome outcome = new Outcome();
        outcome.setName("test");

        Odd odd = new Odd();
        odd.setId(1L);
        odd.setOutcome(outcome);

        Offer offer = new Offer();
        offer.setId(10L);
        offer.setOdds(Collections.singleton(odd));

        Mockito.when(offerRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(offer)));
        Page<OfferDTO> offers = offerService.getOffers(PageRequest.of(1, 1));

        Assertions.assertEquals(10L, offers.getContent().get(0).getOfferId());
    }

}
