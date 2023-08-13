package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendRegistrationConfirmationEmail(String recipientEmail, String confirmationUrl) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject("Registration Confirmation");
        email.setText(
                "Please confirm your registration on BlockTracker by clicking the following link: " + confirmationUrl
        );
        mailSender.send(email);
    }

    @Override
    public void sendRecoveryConfirmationEmail(String recipientEmail, String confirmationUrl) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject("Password Recovery Confirmation");
        email.setText("Please confirm your password change by clicking the following link: " + confirmationUrl);
        mailSender.send(email);
    }

}
