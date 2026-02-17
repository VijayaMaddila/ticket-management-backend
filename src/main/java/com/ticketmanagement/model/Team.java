package com.ticketmanagement.model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

   
    @OneToMany(mappedBy="team")
    private List<User> members; 

    @OneToMany(mappedBy="assignedTeam")
    private List<Ticket> tickets;

    public Team() { }

    public Team(Long id, String name, String description, List<User> members, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.members = members;
        this.tickets = tickets;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

   

    public List<User> getMembers() { return members; }
    public void setMembers(List<User> members) { this.members = members; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}



