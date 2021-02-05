package com.zigix.chatapp.registration;

import com.zigix.chatapp.AppUserService;
import com.zigix.chatapp.entity.AppUser;
import com.zigix.chatapp.entity.AppUserRole;
import com.zigix.chatapp.registration.email.EmailSenderService;
import com.zigix.chatapp.registration.token.ConfirmationToken;
import com.zigix.chatapp.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;
    private final BCryptPasswordEncoder encoder;

    public void register(AppUserDTO appUserDTO) {
        AppUser appUser = new AppUser();

        appUser.setUsername(appUserDTO.getUsername());
        appUser.setEmail(appUserDTO.getEmail());
        appUser.setPassword(encoder.encode(appUserDTO.getPassword()));
        appUser.setAuthority(AppUserRole.USER);

        // save user to database
        appUserService.saveAppUser(appUser);

        // generate some random token as string
        String token = UUID.randomUUID().toString();

        // create confirmation token and associate it with given user
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(24),
                appUser
        );

        // save token to database
        confirmationTokenService.saveToken(confirmationToken);

        // FIXME: this code below might be so much better
        String path = "src/main/resources/templates/email-template.html";
        String emailContent = "";
        File file = new File(path);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line=bufferedReader.readLine()) != null) {
                emailContent += line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String link = "http://localhost:8080/sign-up/confirm?token=" + token;
        emailContent = emailContent.replace("$@username@$", appUser.getUsername());
        emailContent = emailContent.replace("$@link@$", link);

        emailSenderService.sendEmail(appUser.getEmail(), emailContent);
    }

    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .findToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        // check if token is not confirmed yet
        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        // check if token didnt expire yet
        LocalDateTime tokenExpireTime = confirmationToken.getExpiresAt();
        if(tokenExpireTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token already expired");
        }

        confirmationTokenService.setConfirmationTime(token);
        appUserService.enableAppUserAccount(confirmationToken.getOwner().getEmail());
    }
}
