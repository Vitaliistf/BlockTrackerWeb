package org.vitaliistf.blocktracker.service.mapper.impl;

import org.vitaliistf.blocktracker.dto.response.CoinResponseDto;
import org.vitaliistf.blocktracker.service.KuCoinApiService;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;
import org.vitaliistf.blocktracker.models.Coin;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CoinMapper implements ResponseDtoMapper<CoinResponseDto, Coin> {

    private final KuCoinApiService kuCoinApiService;

    @Override
    public CoinResponseDto mapToDto(Coin coin) {
        CoinResponseDto coinResponseDto = new CoinResponseDto();
        coinResponseDto.setId(coin.getId());
        coinResponseDto.setAmount(coin.getAmount());
        coinResponseDto.setSymbol(coin.getSymbol());
        coinResponseDto.setPrice(kuCoinApiService.getPrice(coin.getSymbol()));
        coinResponseDto.setAvgBuyingPrice(coin.getAvgBuyingPrice());

        return coinResponseDto;
    }
}
