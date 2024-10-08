package com.zigix.chatapp.controller;

import com.zigix.chatapp.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class ChatController {

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage registerUserToChat(@Payload ChatMessage chatMessage, Principal principal) {
        log.info("Principal.getName: " + principal.getName());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat/{username}")
    @SendTo("/topic/private/{username}")
    public ChatMessage sendPrivateMessage(@Payload ChatMessage chatMessage,
                                          @DestinationVariable String username) {
        log.info("Private message to " + username);
        return chatMessage;
    }

}
