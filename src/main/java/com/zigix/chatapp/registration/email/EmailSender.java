package com.zigix.chatapp.registration.email;

import org.thymeleaf.templateparser.markup.HTMLTemplateParser;

public interface EmailSender {

    void sendEmail(String to, String htmlEmailTemplate);

}
