package com.bettingapp.bettingapp.repository;

import com.bettingapp.bettingapp.model.Betslip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetslipRepository extends JpaRepository<Betslip, Long> {

    List<Betslip> findAllByPlayer_Id(long playerId, Sort sort);
}
