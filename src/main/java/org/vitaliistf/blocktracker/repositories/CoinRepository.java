package org.vitaliistf.blocktracker.repositories;

import org.vitaliistf.blocktracker.models.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    List<Coin> findAllByPortfolioId(Long portfolioId);

}
