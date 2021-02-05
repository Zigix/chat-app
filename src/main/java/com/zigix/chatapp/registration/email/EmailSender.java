package com.zigix.chatapp.registration.email;

public interface EmailSender {

    void sendEmail(String to, String htmlEmailTemplate);

}
