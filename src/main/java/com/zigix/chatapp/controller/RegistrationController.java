package com.zigix.chatapp.controller;

import com.zigix.chatapp.registration.AppUserDTO;
import com.zigix.chatapp.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/sign-up")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new AppUserDTO());
        return "registration";
    }

    @PostMapping
    public String processSignUpForm(
            @ModelAttribute("user") @Valid AppUserDTO appUserDTO,
            Errors errors) {

        if (errors.hasErrors()) {
            return "registration";
        }

        registrationService.register(appUserDTO);

        return "registration-successful";
    }

    @GetMapping(value = "/confirm", params = {"token"})
    public String confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);

        return "email-confirmed";
    }

}