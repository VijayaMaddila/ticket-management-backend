package com.ticketmanagement.controller;

import com.ticketmanagement.model.Ticket;
import com.ticketmanagement.model.User;
import com.ticketmanagement.model.role.Priority;
import com.ticketmanagement.model.role.RequestType;
import com.ticketmanagement.model.role.Status;
import com.ticketmanagement.service.TicketService;
import com.ticketmanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slack")
public class SlackController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/create-ticket",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createTicket(
            @RequestParam("text") String text) {

        String[] parts = text.split("\\|");

        if (parts.length < 4) {
            return ResponseEntity.ok(
                    "Use format:\n" +
                    "/createticket Title | Description | REQUEST_TYPE | PRIORITY"
            );
        }

        try {

            Ticket ticket = new Ticket();
            ticket.setTitle(parts[0].trim());
            ticket.setDescription(parts[1].trim());

            ticket.setRequestType(
                    RequestType.valueOf(parts[2].trim().toUpperCase()));

            ticket.setPriority(
                    Priority.valueOf(parts[3].trim().toUpperCase()));

            ticket.setStatus(Status.OPEN);

           
            User requester = userRepository.findByEmail("admin@gmail.com")
                    .orElseThrow(() ->
                            new RuntimeException("Default user not found"));

            ticket.setRequester(requester);

            ticketService.saveTicket(ticket);

            return ResponseEntity.ok("Ticket Created Successfully!");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok("Invalid RequestType or Priority value.");
        }
    }
}