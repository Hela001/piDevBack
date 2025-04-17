package com.esprit.ms.pidevbackend.Controllers;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

public class WebSocketTestController {
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("⚡ WebSocket connecté");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("🚫 WebSocket déconnecté");
    }


    @MessageMapping("/send") // Le client envoie à /app/send
    @SendTo("/topic/tasks")  // Le message sera broadcasté à tous ceux abonnés à /topic/tasks
    public String sendTestMessage(String message) {
        return "Reçu: " + message;
    }
}

