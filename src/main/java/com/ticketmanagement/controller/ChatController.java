package com.ticketmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ticketmanagement.service.ChatService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "https://segmento-resolve.vercel.app/")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public String chat(@RequestParam Long userId,
                       @RequestBody String message) {

        return chatService.processMessage(userId, message);
    }
}
