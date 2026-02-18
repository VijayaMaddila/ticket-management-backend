package com.ticketmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ticketmanagement.model.Team;
import com.ticketmanagement.repository.TeamRepository;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins ={"https://segmento-resolve.vercel.app", "http://localhost:5173"})
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    
    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    
    @PostMapping("/create")
    public Team createTeam(@RequestBody Team team) {
        return teamRepository.save(team);
    }
    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public String deleteTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id " + id));
        teamRepository.delete(team);
        return "Team deleted successfully!";
    }
}
