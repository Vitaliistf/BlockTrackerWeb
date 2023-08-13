package org.vitaliistf.blocktracker.service;

import org.vitaliistf.blocktracker.models.User;
import org.vitaliistf.blocktracker.models.WatchlistItem;

import java.util.List;

public interface WatchlistService {

    WatchlistItem save(WatchlistItem item);

    List<WatchlistItem> getAllByClient(User user);

    void deleteById(Long id, User user);

    WatchlistItem getById(Long id, User user);

}
