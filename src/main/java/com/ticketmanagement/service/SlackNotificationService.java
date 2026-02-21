package com.ticketmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class SlackNotificationService {

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendNotification(String message) {
        Map<String, String> payload = new HashMap<>();
        payload.put("text", message);

        try {
            restTemplate.postForObject(webhookUrl, payload, String.class);
        } catch (Exception e) {
            System.err.println("Slack notification failed: " + e.getMessage());
        }
    }
}
