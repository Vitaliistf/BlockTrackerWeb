package org.vitaliistf.blocktracker.controllers.api;

import org.springframework.web.bind.annotation.*;
import org.vitaliistf.blocktracker.dto.response.CoinResponseDto;
import org.vitaliistf.blocktracker.models.Coin;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import org.vitaliistf.blocktracker.service.CoinService;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/coins")
public class CoinController {

    private final CoinService coinService;
    private final UserDetailsService userDetailsService;
    private final ResponseDtoMapper<CoinResponseDto, Coin> responseDtoMapper;

    @GetMapping("/by-portfolio/{portfolioId}")
    public List<CoinResponseDto> getByPortfolioId(Authentication authentication, @PathVariable Long portfolioId) {
        return coinService.getAllByPortfolioId(portfolioId, userDetailsService.getClient(authentication))
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CoinResponseDto get(Authentication authentication, @PathVariable Long id) {
        return responseDtoMapper.mapToDto(
                coinService.getById(id, userDetailsService.getClient(authentication))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication authentication, @PathVariable Long id) {
        coinService.deleteById(id, userDetailsService.getClient(authentication));
    }
}