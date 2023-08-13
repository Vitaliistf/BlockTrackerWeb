package org.vitaliistf.blocktracker.controllers;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.service.RegistrationService;
import org.vitaliistf.blocktracker.util.validation.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpPage(@ModelAttribute("user") User user) {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") @Valid User user,
                                BindingResult bindingResult,
                                Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        registrationService.register(user);
        model.addAttribute("message", "Check your email for confirmation letter. " +
                "First you need to confirm your email, then you will be able to log in.");
        return "signup";
    }

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        registrationService.confirmRegistration(token);
        return "operation-confirmed";
    }
}
