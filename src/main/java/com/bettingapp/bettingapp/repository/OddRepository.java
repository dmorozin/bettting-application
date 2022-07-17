package com.bettingapp.bettingapp.repository;

import com.bettingapp.bettingapp.model.Odd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OddRepository extends JpaRepository<Odd, Long> {

}
