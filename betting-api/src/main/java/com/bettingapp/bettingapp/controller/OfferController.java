package com.bettingapp.bettingapp.controller;

import com.bettingapp.bettingapp.dto.OfferDTO;
import com.bettingapp.bettingapp.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<OfferDTO>> getOffers(@RequestParam int page,
                                                    @RequestParam int size) {

        return ResponseEntity.ok(offerService.getOffers(PageRequest.of(page, size)));
    }
}
