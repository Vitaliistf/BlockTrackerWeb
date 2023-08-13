package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.exception.ConfirmationTokenNotFoundException;
import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.ConfirmationToken;
import org.vitaliistf.blocktracker.repositories.UserRepository;
import org.vitaliistf.blocktracker.repositories.ConfirmationTokenRepository;
import org.vitaliistf.blocktracker.service.MailService;
import org.vitaliistf.blocktracker.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final MailService mailService;

    private final PasswordEncoder encoder;

    @Override
    public void register(User user) {
        String token = UUID.randomUUID().toString();
        user.setEnabled(false);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(token, user);
        confirmationTokenRepository.save(confirmationToken);

        String confirmationUrl = "http://localhost:8080/confirm?token=" + token;
        mailService.sendRegistrationConfirmationEmail(user.getEmail(), confirmationUrl);
    }

    @Override
    public void confirmRegistration(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken.isEmpty() || confirmationToken.get().isExpired()) {
            throw new ConfirmationTokenNotFoundException("Confirmation token not found or expired.");
        } else {
            User user = confirmationToken.get().getUser();
            user.setEnabled(true);
            userRepository.save(user);
            confirmationTokenRepository.delete(confirmationToken.get());
        }
    }
}
