package com.zigix.chatapp.registration;

import com.zigix.chatapp.AppUserService;
import com.zigix.chatapp.entity.AppUser;
import com.zigix.chatapp.entity.AppUserRole;
import com.zigix.chatapp.exception.*;
import com.zigix.chatapp.registration.email.EmailSenderService;
import com.zigix.chatapp.registration.token.ConfirmationToken;
import com.zigix.chatapp.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

        // generate confirmation token for user
        ConfirmationToken confirmationToken = generateConfirmationTokenForUser(appUser);

        // save token to database
        confirmationTokenService.saveToken(confirmationToken);

        String rootPath = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
        String link = rootPath + "/sign-up/confirm?token=" + confirmationToken.getToken();

        sendConfirmationEmail(appUser.getEmail(), appUser.getUsername(), link);
    }

    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .findToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));

        // check if token is not confirmed yet
        if(confirmationToken.getConfirmedAt() != null) {
            throw new EmailConfirmedException("Email already confirmed");
        }

        // check if token didnt expire yet
        LocalDateTime tokenExpireTime = confirmationToken.getExpiresAt();
        if(tokenExpireTime.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token already expired", token);
        }

        confirmationTokenService.setConfirmationTime(token);
        appUserService.confirmUserEmail(confirmationToken.getOwner().getEmail());
    }

    private void sendConfirmationEmail(String email, String username, String link) {

        Map<String, String> toReplace = new HashMap<>();
        toReplace.put("$@username@$", username);
        toReplace.put("$@link@$", link);

        String emailContent = prepareEmailContent(toReplace);

        emailSenderService.sendEmail(email, emailContent);
    }

    private String prepareEmailContent(Map<String, String> toBeReplaced) {
        String pathToEmailTemplate = "email-template.html";
        String emailContent = "";
        File file = new File(pathToEmailTemplate);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line=bufferedReader.readLine()) != null) {
                emailContent += line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (String key : toBeReplaced.keySet()) {
            emailContent = emailContent.replace(key, toBeReplaced.get(key));
        }

        return emailContent;
    }

    private ConfirmationToken generateConfirmationTokenForUser(AppUser owner) {
        String token = UUID.randomUUID().toString();

        return new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(24),
                owner
        );
    }
}
