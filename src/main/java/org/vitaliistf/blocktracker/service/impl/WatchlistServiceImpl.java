package org.vitaliistf.blocktracker.service.impl;

import org.vitaliistf.blocktracker.exception.PermissionDeniedException;
import org.vitaliistf.blocktracker.exception.ItemExistsException;
import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.WatchlistItem;
import org.vitaliistf.blocktracker.repositories.WatchlistItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitaliistf.blocktracker.service.WatchlistService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistItemRepository watchlistItemRepository;

    @Override
    public WatchlistItem save(WatchlistItem item) {
        if (watchlistItemRepository.findBySymbolAndUserId(
                item.getSymbol(),
                item.getUser().getId()
        ).isPresent()) {
            throw new ItemExistsException("You already have this coin in watchlist.");
        }
        return watchlistItemRepository.save(item);
    }

    @Override
    public List<WatchlistItem> getAllByClient(User user) {
        return watchlistItemRepository.findAllByUserId(user.getId());
    }

    @Override
    public void deleteById(Long id, User user) {
        WatchlistItem watchlistItem = watchlistItemRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find such coin.")
        );
        if (!watchlistItem.getUser().equals(user)) {
            throw new PermissionDeniedException("You cannot delete this coin.");
        }
        watchlistItemRepository.deleteById(id);
    }

    @Override
    public WatchlistItem getById(Long id, User user) {
        WatchlistItem watchlistItem = watchlistItemRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cannot find such coin.")
        );
        if (!watchlistItem.getUser().equals(user)) {
            throw new PermissionDeniedException("You cannot view this coin.");
        }
        return watchlistItemRepository.getOne(id);
    }
}
