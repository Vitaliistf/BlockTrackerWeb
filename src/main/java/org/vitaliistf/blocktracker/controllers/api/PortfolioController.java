package org.vitaliistf.blocktracker.controllers.api;

import org.vitaliistf.blocktracker.dto.request.PortfolioRequestDto;
import org.vitaliistf.blocktracker.dto.response.PortfolioResponseDto;
import org.vitaliistf.blocktracker.models.Portfolio;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import org.vitaliistf.blocktracker.service.PortfolioService;
import org.vitaliistf.blocktracker.service.mapper.RequestDtoMapper;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserDetailsService userDetailsService;
    private final ResponseDtoMapper<PortfolioResponseDto, Portfolio> responseDtoMapper;
    private final RequestDtoMapper<PortfolioRequestDto, Portfolio> requestDtoMapper;

    @PostMapping
    public PortfolioResponseDto create(Authentication authentication,
                                       @Valid @RequestBody PortfolioRequestDto portfolioDto) {
        Portfolio portfolioToSave = requestDtoMapper.mapToModel(portfolioDto);
        portfolioToSave.setUser(userDetailsService.getClient(authentication));
        Portfolio portfolio = portfolioService.save(portfolioToSave);
        return responseDtoMapper.mapToDto(portfolio);
    }

    @PutMapping("/{id}")
    public PortfolioResponseDto update(Authentication authentication,
                                       @PathVariable Long id,
                                       @Valid @RequestBody PortfolioRequestDto portfolioDto) {
        Portfolio portfolioToSave = requestDtoMapper.mapToModel(portfolioDto);
        portfolioToSave.setUser(userDetailsService.getClient(authentication));
        return responseDtoMapper.mapToDto(portfolioService.update(id, portfolioToSave));
    }

    @GetMapping("/{id}")
    public PortfolioResponseDto get(Authentication authentication, @PathVariable Long id) {
        return responseDtoMapper.mapToDto(
                portfolioService.getById(id, userDetailsService.getClient(authentication))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication authentication, @PathVariable Long id) {
        portfolioService.deleteById(id, userDetailsService.getClient(authentication));
    }

    @GetMapping
    public List<PortfolioResponseDto> getPortfoliosForClient(Authentication authentication) {
        List<Portfolio> portfolioList = portfolioService.getAllPortfolios(
                userDetailsService.getClient(authentication)
        );
        return portfolioList
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
