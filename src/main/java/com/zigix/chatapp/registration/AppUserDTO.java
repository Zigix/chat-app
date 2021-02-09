package com.zigix.chatapp.registration;

import com.zigix.chatapp.validation.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
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
}
