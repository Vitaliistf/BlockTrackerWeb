package org.vitaliistf.blocktracker.repositories;

import org.vitaliistf.blocktracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User getByEmail(String email);

}
