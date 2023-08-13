package org.vitaliistf.blocktracker.service;

import org.vitaliistf.blocktracker.models.User;

public interface PasswordRecoveryService {

    void recover(User user);

    void confirmRecovery(String token);
}
