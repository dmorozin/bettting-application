package com.bettingapp.bettingapp.service.impl;

import com.bettingapp.bettingapp.dto.BetInsertionDTO;
import com.bettingapp.bettingapp.dto.PlayerInfoDTO;
import com.bettingapp.bettingapp.exception.ResourceNotFoundException;
import com.bettingapp.bettingapp.model.*;
import com.bettingapp.bettingapp.repository.BetRepository;
import com.bettingapp.bettingapp.repository.BetslipRepository;
import com.bettingapp.bettingapp.repository.PlayerRepository;
import com.bettingapp.bettingapp.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static com.bettingapp.bettingapp.utils.Constants.PLAYER_NOT_FOUND;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final BetslipRepository betslipRepository;
    private final BetRepository betRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             BetslipRepository betslipRepository,
                             BetRepository betRepository,
                             EntityManager entityManager) {

        this.playerRepository = playerRepository;
        this.betslipRepository = betslipRepository;
        this.betRepository = betRepository;
        this.entityManager = entityManager;
    }

    @Override
    public PlayerInfoDTO getPlayerInfo(long playerId) {

        Player player = getPlayer(playerId);
        return new PlayerInfoDTO(player.getId(),
                player.getName(),
                player.getWalletAmount());
    }

    @Override
    public long addNewBetslip(long playerId, BetInsertionDTO betInsertionDTO) {

        Player player = getPlayer(playerId);
        player.setWalletAmount(betInsertionDTO.getWalletAmount());
        playerRepository.save(player);

        Betslip betslip = Betslip.builder()
                .createdOn(LocalDateTime.now())
                .player(player)
                .build();

        Betslip newBetslip = betslipRepository.save(betslip);

        for (BetInsertionDTO.BetDTO betDTO : betInsertionDTO.getBets()) {
            Odd odd = entityManager.getReference(Odd.class, betDTO.getOddId());
            Offer offer = entityManager.getReference(Offer.class, betDTO.getOfferId());
            Bet bet = Bet.builder()
                    .odd(odd)
                    .offer(offer)
                    .betslip(newBetslip)
                    .build();
            betRepository.save(bet);
        }

        return newBetslip.getId();
    }

    private Player getPlayer(long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(String.format(PLAYER_NOT_FOUND, playerId)));
    }
}
