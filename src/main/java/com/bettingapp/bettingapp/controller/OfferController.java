package com.bettingapp.bettingapp.controller;

import com.bettingapp.bettingapp.dto.OfferDTO;
import com.bettingapp.bettingapp.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/offer")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {

        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<Page<OfferDTO>> getOffers(@PageableDefault Pageable pageable) {

        return ResponseEntity.ok(offerService.getOffers(pageable));
    }
}
