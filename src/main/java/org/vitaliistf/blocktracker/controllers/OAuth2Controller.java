package org.vitaliistf.blocktracker.controllers;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private final UserDetailsService userDetailsService;

    @GetMapping("/google/callback")
    public String googleCallback(Authentication authentication) {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        User user = new User(oauth2User.getAttribute("email"));

        if (!userDetailsService.emailExists(user.getEmail())) {
            userDetailsService.save(user);
        }

        return "redirect:/";
    }
}

