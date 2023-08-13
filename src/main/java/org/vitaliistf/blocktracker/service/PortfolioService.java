package org.vitaliistf.blocktracker.service;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.Portfolio;

import java.util.List;

public interface PortfolioService {

    Portfolio save(Portfolio portfolio);

    Portfolio getById(Long id, User user);

    void deleteById(Long id, User user);

    Portfolio update(Long id, Portfolio portfolio);

    List<Portfolio> getAllPortfolios(User user);

}
