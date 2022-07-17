package com.bettingapp.bettingapp.service;

import com.bettingapp.bettingapp.dto.OfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {

    Page<OfferDTO> getOffers(Pageable pageable);
}
