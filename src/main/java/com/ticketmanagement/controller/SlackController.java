package com.ticketmanagement.controller;

import com.ticketmanagement.model.Ticket;
import com.ticketmanagement.model.User;
import com.ticketmanagement.model.role.Priority;
import com.ticketmanagement.model.role.RequestType;
import com.ticketmanagement.model.role.Role;
import com.ticketmanagement.model.role.Status;
import com.ticketmanagement.repository.UserRepository;
import com.ticketmanagement.service.TicketService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slack")
public class SlackController{

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/create-ticket",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createTicket(
            @RequestParam("text") String text,
            @RequestParam("user_name") String slackUsername) {

       
        new Thread(() -> {
            try {
                processTicket(text, slackUsername);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return ResponseEntity.ok("Ticket is being created...");
    }
    private void processTicket(String text, String slackUsername) {

        String[] parts = text.split("\\|");

        if (parts.length < 6) {
            return;
        }

        Ticket ticket = new Ticket();
        ticket.setTitle(parts[0].trim());
        ticket.setDescription(parts[1].trim());
        ticket.setRequestType(RequestType.valueOf(parts[2].trim().toUpperCase()));
        ticket.setPriority(Priority.valueOf(parts[3].trim().toUpperCase()));
        ticket.setRequestedDataset(parts[4].trim());
        ticket.setDueDate(LocalDate.parse(parts[5].trim()));
        ticket.setStatus(Status.OPEN);

        String email = slackUsername + "@slack.com";

        User requester = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setName(slackUsername);
                    newUser.setEmail(email);
                    newUser.setPassword(passwordEncoder.encode("default123"));
                    newUser.setRole(Role.requester);
                    return userRepository.save(newUser);
                });

        ticket.setRequester(requester);
        ticketService.saveTicket(ticket);
    }
}