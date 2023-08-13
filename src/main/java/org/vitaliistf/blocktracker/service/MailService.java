package org.vitaliistf.blocktracker.service;

public interface MailService {

    void sendRegistrationConfirmationEmail(String recipientEmail, String confirmationUrl);

    void sendRecoveryConfirmationEmail(String recipientEmail, String confirmationUrl);

}
