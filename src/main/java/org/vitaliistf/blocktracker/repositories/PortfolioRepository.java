package org.vitaliistf.blocktracker.repositories;

import org.vitaliistf.blocktracker.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findAllByUserId(Long userId);

    Optional<Portfolio> findByNameAndUserId(String name, Long userId);

}
