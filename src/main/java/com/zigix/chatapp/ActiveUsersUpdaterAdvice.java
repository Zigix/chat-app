package com.zigix.chatapp;

import com.zigix.chatapp.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class ActiveUsersUpdaterAdvice {

    SimpMessageSendingOperations messageSendingOperations;
    ActiveUsers activeUsers;

    @Pointcut("execution(* com.zigix.chatapp.controller.ChatController.registerUserToChat(..))")
    private void onUserConnect() {}

    @Pointcut("execution(* com.zigix.chatapp.WebSocketEventListener.onDisconnectUserEventHandler(..))")
    private void onUserDisconnect() {}

    @After("onUserConnect() || onUserDisconnect()")
    private void updateActiveUsers() {
        log.info("Active aspect advice");
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.UPDATE);
        chatMessage.setActiveUsers(activeUsers.getActiveUsers().toArray(new String[0]));

        messageSendingOperations.convertAndSend("/topic/public", chatMessage);
    }
}
