package com.zigix.chatapp.registration.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailData {
    private String recipient;
    private String subject;
    private String content;
}
