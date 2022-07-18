package com.bettingapp.bettingapp.repository;

import com.bettingapp.bettingapp.model.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

}