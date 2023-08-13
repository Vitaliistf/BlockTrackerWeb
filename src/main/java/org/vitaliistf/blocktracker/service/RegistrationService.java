package org.vitaliistf.blocktracker.service;

import org.vitaliistf.blocktracker.models.User;

public interface RegistrationService {

    void register(User user);

    void confirmRegistration(String token);

}
