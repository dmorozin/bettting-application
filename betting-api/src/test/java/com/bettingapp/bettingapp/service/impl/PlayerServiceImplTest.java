package com.bettingapp.bettingapp.service.impl;

import com.bettingapp.bettingapp.dto.BetInsertionDTO;
import com.bettingapp.bettingapp.dto.BetslipBetDTO;
import com.bettingapp.bettingapp.dto.PlayerBetslipsDTO;
import com.bettingapp.bettingapp.dto.PlayerInfoDTO;
import com.bettingapp.bettingapp.model.*;
import com.bettingapp.bettingapp.repository.BetRepository;
import com.bettingapp.bettingapp.repository.BetslipRepository;
import com.bettingapp.bettingapp.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class PlayerServiceImplTest {
    @InjectMocks
    PlayerServiceImpl playerService;

    @Mock
    PlayerRepository playerRepository;
    @Mock
    BetslipRepository betslipRepository;
    @Mock
    BetRepository betRepository;
    @Mock
    EntityManager em;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPlayerInfo() {
        long id = 1L;
        Player player = getPlayer(id);

        Mockito.when(playerRepository.findById(id)).thenReturn(Optional.of(player));
        PlayerInfoDTO playerInfo = playerService.getPlayerInfo(id);
        Assertions.assertEquals(playerInfo.getId(), id);
    }

    @Test
    void testAddNewBetslip() {
        long id = 1L;
        Player player = getPlayer(id);
        BetInsertionDTO betInsertionDTO = new BetInsertionDTO(10,
                20,
                Collections.singletonList(new BetInsertionDTO.BetDTO(1, 1)));
        BetInsertionDTO.BetDTO betDTO = betInsertionDTO.getBets().get(0);

        Betslip newBetslip = Betslip.builder().id(10L).build();
        Odd odd = new Odd();
        odd.setId(1L);
        Offer offer = new Offer();
        offer.setId(1L);

        Bet bet = Bet.builder()
                .id(100L)
                .offer(offer)
                .odd(odd)
                .betslip(newBetslip).build();

        Mockito.when(playerRepository.findById(id)).thenReturn(Optional.of(player));
        Mockito.when(playerRepository.save(any(Player.class))).thenReturn(player);
        Mockito.when(betslipRepository.save(any(Betslip.class))).thenReturn(newBetslip);
        Mockito.when(em.getReference(Odd.class, betDTO.getOddId())).thenReturn(odd);
        Mockito.when(em.getReference(Offer.class, betDTO.getOfferId())).thenReturn(offer);
        Mockito.when(betRepository.save(any(Bet.class))).thenReturn(bet);

        long newBetslipId = playerService.addNewBetslip(id, betInsertionDTO);
        Assertions.assertEquals(10L, newBetslipId);
    }

    @Test
    void testGetPlayerBets() {
        long id = 1L;
        Sort sort = Sort.by(Sort.Direction.DESC, "createdOn");
        Betslip betslip = getBetslip();
        Mockito.when(betslipRepository.findAllByPlayer_Id(id, sort))
                .thenReturn(Collections.singletonList(betslip));

        List<PlayerBetslipsDTO> playerBets = playerService.getPlayerBets(id);
        Assertions.assertEquals(20, playerBets.get(0).getGain());
    }

    @Test
    void tetGetBetslipBets() {
        long id = 1l;
        Betslip betslip = getBetslip();

        Outcome outcome = new Outcome();
        Odd odd = new Odd();
        odd.setOutcome(outcome);
        Offer offer = new Offer();
        offer.setHomeTeam("home");
        offer.setAwayTeam("away");

        betslip.setBets(Collections.singleton(new Bet(1L, betslip, offer, odd)));
        Mockito.when(betslipRepository.findById(id)).thenReturn(Optional.of(betslip));

        List<BetslipBetDTO> betlipBets = playerService.getBetlipBets(id);
        Assertions.assertEquals("home", betlipBets.get(0).getHomeTeam());
    }

    private Betslip getBetslip() {
        Player player = getPlayer(1L);
        Betslip betslip = Betslip.builder()
                .id(1L)
                .player(player)
                .createdOn(LocalDateTime.now())
                .stake(10)
                .gain(20)
                .build();
        return betslip;
    }

    private Player getPlayer(long id) {
        Player player = new Player();
        player.setId(id);
        return player;
    }
}
