package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.exception.ItemExistsException;
import org.vitaliistf.blocktracker.exception.PermissionDeniedException;
import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.Portfolio;
import org.vitaliistf.blocktracker.repositories.PortfolioRepository;
import org.vitaliistf.blocktracker.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public Portfolio save(Portfolio portfolio) {
        if (portfolioRepository.findByNameAndUserId(
                portfolio.getName(),
                portfolio.getUser().getId()
        ).isPresent()) {
            throw new ItemExistsException("You already have portfolio with this name.");
        }
        return portfolioRepository.save(portfolio);

    }

    @Override
    public Portfolio getById(Long id, User user) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find portfolio with such id.")
        );
        if (!portfolio.getUser().equals(user)) {
            throw new PermissionDeniedException("You have no permission to view this portfolio.");
        }
        return portfolio;
    }

    @Override
    public void deleteById(Long id, User user) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find portfolio with such id.")
        );

        if (portfolio.getUser().equals(user)) {
            portfolioRepository.deleteById(id);
        } else {
            throw new PermissionDeniedException("You have no permission to delete this portfolio.");
        }
    }

    @Override
    public Portfolio update(Long id, Portfolio portfolio) {
        if (portfolioRepository.findByNameAndUserId(
                portfolio.getName(),
                portfolio.getUser().getId()
        ).isPresent()) {
            throw new ItemExistsException("You already have portfolio with this name.");
        }

        Portfolio portfolioFromDb = portfolioRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find portfolio with such id.")
        );
        if (!portfolioFromDb.getUser().equals(portfolio.getUser())) {
            throw new PermissionDeniedException("You have no permission to update this portfolio.");
        }
        portfolioFromDb.setName(portfolio.getName());
        return save(portfolioFromDb);
    }

    @Override
    public List<Portfolio> getAllPortfolios(User user) {
        return portfolioRepository.findAllByUserId(user.getId());
    }

}
