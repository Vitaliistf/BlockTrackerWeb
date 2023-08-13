package org.vitaliistf.blocktracker.service.mapper.impl;

import org.vitaliistf.blocktracker.dto.request.PortfolioRequestDto;
import org.vitaliistf.blocktracker.dto.response.PortfolioResponseDto;
import org.vitaliistf.blocktracker.service.mapper.RequestDtoMapper;
import org.vitaliistf.blocktracker.models.Portfolio;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper implements RequestDtoMapper<PortfolioRequestDto, Portfolio>,
        ResponseDtoMapper<PortfolioResponseDto, Portfolio> {

    @Override
    public Portfolio mapToModel(PortfolioRequestDto dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(dto.getName());

        return portfolio;
    }

    @Override
    public PortfolioResponseDto mapToDto(Portfolio portfolio) {
        PortfolioResponseDto portfolioResponseDto = new PortfolioResponseDto();
        portfolioResponseDto.setId(portfolio.getId());
        portfolioResponseDto.setName(portfolio.getName());

        return portfolioResponseDto;
    }
}
