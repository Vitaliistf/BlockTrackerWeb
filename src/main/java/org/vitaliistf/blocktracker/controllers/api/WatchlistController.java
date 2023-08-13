package org.vitaliistf.blocktracker.controllers.api;

import org.springframework.web.bind.annotation.*;
import org.vitaliistf.blocktracker.dto.request.WatchlistItemRequestDto;
import org.vitaliistf.blocktracker.dto.response.WatchlistItemResponseDto;
import org.vitaliistf.blocktracker.models.WatchlistItem;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import org.vitaliistf.blocktracker.service.WatchlistService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.vitaliistf.blocktracker.service.mapper.RequestDtoMapper;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/watchlist")
@AllArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final UserDetailsService userDetailsService;
    private final ResponseDtoMapper<WatchlistItemResponseDto, WatchlistItem> responseDtoMapper;
    private final RequestDtoMapper<WatchlistItemRequestDto, WatchlistItem> requestDtoMapper;

    @PostMapping
    public WatchlistItemResponseDto addToWatchlist(Authentication authentication,
                                                   @Valid @RequestBody WatchlistItemRequestDto watchlistItemDto) {
        WatchlistItem watchlistItem = requestDtoMapper.mapToModel(watchlistItemDto);
        watchlistItem.setUser(userDetailsService.getClient(authentication));
        return responseDtoMapper.mapToDto(watchlistService.save(watchlistItem));
    }

    @GetMapping
    public List<WatchlistItemResponseDto> getWatchlist(Authentication authentication) {
        return watchlistService.getAllByClient(userDetailsService.getClient(authentication))
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public WatchlistItemResponseDto get(Authentication authentication, @PathVariable Long id) {
        return responseDtoMapper.mapToDto(
                watchlistService.getById(id, userDetailsService.getClient(authentication))
        );
    }

    @DeleteMapping("/{id}")
    public void getWatchlist(Authentication authentication, @PathVariable Long id) {
        watchlistService.deleteById(id, userDetailsService.getClient(authentication));
    }

}
