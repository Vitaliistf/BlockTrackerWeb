package org.vitaliistf.blocktracker.controllers;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import org.vitaliistf.blocktracker.service.PasswordRecoveryService;
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
public class PasswordRecoveryController {

    private final UserDetailsService userDetailsService;
    private final PasswordRecoveryService passwordRecoveryService;

    @GetMapping("/forgot_password")
    public String getForgotPasswordPage(@ModelAttribute("user") User user) {
        return "forgot-password";
    }

    @PostMapping("/process_recovery")
    public String processRecovery(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                  Model model) {
        if (!userDetailsService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "", "User not found.");
            return "forgot-password";
        }
        passwordRecoveryService.recover(user);
        model.addAttribute("message", "Check your email for confirmation letter. " +
                "First you need to confirm your email, then you will be able to log in.");
        return "forgot-password";
    }

    @GetMapping("/confirm_recovery")
    public String confirmRegistration(@RequestParam("token") String token) {
        passwordRecoveryService.confirmRecovery(token);
        return "operation-confirmed";
    }
}
