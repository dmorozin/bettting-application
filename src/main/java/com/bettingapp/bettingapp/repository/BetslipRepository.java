package com.bettingapp.bettingapp.repository;

import com.bettingapp.bettingapp.model.Betslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetslipRepository extends JpaRepository<Betslip, Long> {

}
