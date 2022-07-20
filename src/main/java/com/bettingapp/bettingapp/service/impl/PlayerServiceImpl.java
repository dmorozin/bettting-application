package com.bettingapp.bettingapp.service.impl;

import com.bettingapp.bettingapp.dto.BetInsertionDTO;
import com.bettingapp.bettingapp.dto.PlayerBetslipsDTO;
import com.bettingapp.bettingapp.dto.PlayerInfoDTO;
import com.bettingapp.bettingapp.exception.ResourceNotFoundException;
import com.bettingapp.bettingapp.model.*;
import com.bettingapp.bettingapp.repository.BetRepository;
import com.bettingapp.bettingapp.repository.BetslipRepository;
import com.bettingapp.bettingapp.repository.PlayerRepository;
import com.bettingapp.bettingapp.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.bettingapp.bettingapp.utils.Constants.PLAYER_NOT_FOUND;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final BetslipRepository betslipRepository;
    private final BetRepository betRepository;
    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             BetslipRepository betslipRepository,
                             BetRepository betRepository,
                             EntityManager em) {

        this.playerRepository = playerRepository;
        this.betslipRepository = betslipRepository;
        this.betRepository = betRepository;
        this.em = em;
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
        double updatedWalletAmount = player.getWalletAmount() - betInsertionDTO.getStake();
        player.setWalletAmount(updatedWalletAmount);
        playerRepository.save(player);

        Betslip betslip = Betslip.builder()
                .createdOn(LocalDateTime.now())
                .player(player)
                .stake(betInsertionDTO.getStake())
                .gain(betInsertionDTO.getGain())
                .build();

        Betslip newBetslip = betslipRepository.save(betslip);

        for (BetInsertionDTO.BetDTO betDTO : betInsertionDTO.getBets()) {
            Odd odd = em.getReference(Odd.class, betDTO.getOddId());
            Offer offer = em.getReference(Offer.class, betDTO.getOfferId());
            Bet bet = Bet.builder()
                    .odd(odd)
                    .offer(offer)
                    .betslip(newBetslip)
                    .build();
            betRepository.save(bet);
        }

        return newBetslip.getId();
    }

    @Override
    public List<PlayerBetslipsDTO> getPlayerBets(long playerId) {

        List<Betslip> betslips = betslipRepository.findAllByPlayer_Id(playerId, Sort.by(Sort.Direction.DESC, "createdOn"));
        List<PlayerBetslipsDTO> playerBetslipsDTOS = new ArrayList<>();

        for (Betslip betslip : betslips) {
            Set<Bet> bets = betslip.getBets();
            List<PlayerBetslipsDTO.PlacedBetsDTO> placedBets = new ArrayList<>();

            for (Bet bet : bets) {
                placedBets.add(new PlayerBetslipsDTO.PlacedBetsDTO(bet.getOffer().getHomeTeam(),
                        bet.getOffer().getAwayTeam(),
                        bet.getOdd().getOutcome().getName(),
                        bet.getOdd().getValue()));
            }

            playerBetslipsDTOS.add(new PlayerBetslipsDTO(betslip.getId(),
                    betslip.getCreatedOn().toString(),
                    placedBets,
                    betslip.getStake(),
                    betslip.getGain()));
        }

        return playerBetslipsDTOS;
    }

    private Player getPlayer(long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PLAYER_NOT_FOUND, playerId)));
    }
}
