package com.ticketmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketmanagement.model.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {

}
