package org.vitaliistf.blocktracker.repositories;

import org.vitaliistf.blocktracker.models.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, Long> {
    List<WatchlistItem> findAllByUserId(Long userId);

    Optional<WatchlistItem> findBySymbolAndUserId(String symbol, Long userId);
}
