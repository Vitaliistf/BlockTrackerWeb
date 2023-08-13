package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.repositories.UserRepository;
import org.vitaliistf.blocktracker.security.UserDetailsImpl;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(
                () -> new UsernameNotFoundException("User not found.")
        );
        return new UserDetailsImpl(user);
    }

    @Override
    public User getClient(Authentication authentication) {
        User client;
        try {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            client = userRepository.findByEmail(user.getAttribute("email")).orElseThrow(
                    () -> new UsernameNotFoundException("User not found.")
            );
        } catch (Exception ignored) {
            client = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        }
        return client;
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
