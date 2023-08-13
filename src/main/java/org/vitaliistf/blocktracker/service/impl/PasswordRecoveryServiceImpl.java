package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.exception.ConfirmationTokenNotFoundException;
import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.ConfirmationToken;
import org.vitaliistf.blocktracker.repositories.UserRepository;
import org.vitaliistf.blocktracker.repositories.ConfirmationTokenRepository;
import org.vitaliistf.blocktracker.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vitaliistf.blocktracker.service.PasswordRecoveryService;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final MailService mailService;

    private final PasswordEncoder encoder;

    @Override
    public void recover(User user) {
        String token = UUID.randomUUID().toString();
        User userFromDb = userRepository.getByEmail(user.getEmail());
        userFromDb.setEnabled(false);

        String encodedPassword = encoder.encode(user.getPassword());
        userFromDb.setPassword(encodedPassword);

        userRepository.save(userFromDb);

        ConfirmationToken confirmationToken = new ConfirmationToken(token, userFromDb);
        confirmationTokenRepository.save(confirmationToken);

        String confirmationUrl = "http://localhost:8080/confirm_recovery?token=" + token;
        mailService.sendRecoveryConfirmationEmail(user.getEmail(), confirmationUrl);
    }

    @Override
    public void confirmRecovery(String token) {
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
