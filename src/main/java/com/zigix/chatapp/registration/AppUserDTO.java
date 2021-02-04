package com.zigix.chatapp.registration;

import com.zigix.chatapp.validation.FieldMatch;
import com.zigix.chatapp.validation.SecurityType;
import com.zigix.chatapp.validation.UniqueUsername;
import com.zigix.chatapp.validation.ValidPassword;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword")
public class AppUserDTO {

    @Email(message = " invalid email format")
    @UniqueUsername(message = " this email already exists")
    @NotEmpty(message = " is required")
    private String email;

    @ValidPassword(security = SecurityType.WEAK)
    private String password;

    private String matchingPassword;
}
