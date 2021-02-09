package com.zigix.chatapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/public-chat")
    public String showPublicChatPage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "public-chat";
    }
}
