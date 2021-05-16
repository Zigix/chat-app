package com.zigix.chatapp.registration;

import com.zigix.chatapp.validation.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "Confirmed password doesn't match")
public class AppUserDTO {

    @NotBlank(message = "Username is required")
    @UniqueUsername
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = " invalid email format")
    @UniqueEmail
    private String email;

    @ValidPassword(security = SecurityType.WEAK)
    private String password;

    private String matchingPassword;

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
