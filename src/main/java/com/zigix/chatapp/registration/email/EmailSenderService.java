package com.zigix.chatapp.registration.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class EmailSenderService implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmail(EmailData emailData) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            messageHelper.setFrom("zigix@chatix.pl", "Chatix");
            messageHelper.setTo(emailData.getRecipient());
            messageHelper.setSubject(emailData.getSubject());
            messageHelper.setText(emailData.getContent(), true);

            mailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            throw new IllegalStateException("Filed to send email");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
