package com.zigix.chatapp.controller;

import com.zigix.chatapp.AppUserService;
import com.zigix.chatapp.registration.AppUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/sign-up")
@AllArgsConstructor
public class RegistrationController {

    private final AppUserService appUserService;

    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new AppUserDTO());
        return "registration";
    }

    @PostMapping
    public String processSignUpForm(
            @ModelAttribute("user") @Valid AppUserDTO appUserDTO,
            Errors errors) {

        if(errors.hasErrors()) {
            return "registration";
        }

        // sign up user using service
        appUserService.signUpUser(appUserDTO);

        return "registration-successful";
    }
}
