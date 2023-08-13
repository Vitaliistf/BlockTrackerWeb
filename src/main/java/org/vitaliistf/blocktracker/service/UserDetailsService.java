package org.vitaliistf.blocktracker.service;

import org.vitaliistf.blocktracker.models.User;
import org.springframework.security.core.Authentication;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

    User getClient(Authentication authentication);

    boolean emailExists(String email);

    void save(User user);

}
