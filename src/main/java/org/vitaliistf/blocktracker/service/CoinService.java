package org.vitaliistf.blocktracker.service;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.Coin;

import java.util.List;

public interface CoinService {
    void updateCoinData(Long coinId);

    Coin save(Coin coin);

    Coin getById(Long id, User user);

    List<Coin> getAllByPortfolioId(Long portfolioId, User user);

    void deleteById(Long id, User user);
}
