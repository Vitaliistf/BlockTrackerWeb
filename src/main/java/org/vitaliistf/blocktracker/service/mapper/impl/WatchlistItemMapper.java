package org.vitaliistf.blocktracker.service.mapper.impl;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import org.vitaliistf.blocktracker.dto.request.WatchlistItemRequestDto;
import org.vitaliistf.blocktracker.dto.response.WatchlistItemResponseDto;
import org.vitaliistf.blocktracker.models.WatchlistItem;
import org.vitaliistf.blocktracker.service.KuCoinApiService;
import org.vitaliistf.blocktracker.service.mapper.RequestDtoMapper;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class WatchlistItemMapper implements RequestDtoMapper<WatchlistItemRequestDto, WatchlistItem>,
        ResponseDtoMapper<WatchlistItemResponseDto, WatchlistItem> {

    private final KuCoinApiService kuCoinApiService;

    @Override
    public WatchlistItem mapToModel(WatchlistItemRequestDto dto) {
        WatchlistItem item = new WatchlistItem();
        item.setSymbol(dto.getSymbol());
        return item;
    }

    @Override
    public WatchlistItemResponseDto mapToDto(WatchlistItem watchlistItem) {
        WatchlistItemResponseDto dto = new WatchlistItemResponseDto();
        dto.setId(watchlistItem.getId());
        dto.setSymbol(watchlistItem.getSymbol());

        JsonObject object = kuCoinApiService.getFullInfo(dto.getSymbol());

        dto.setLow(object.get("low").getAsBigDecimal());
        dto.setHigh(object.get("high").getAsBigDecimal());
        dto.setPrice(object.get("last").getAsBigDecimal());
        dto.setVol(object.get("vol").getAsBigDecimal());
        dto.setVolValue(object.get("volValue").getAsBigDecimal());
        dto.setChangePrice(object.get("changePrice").getAsBigDecimal());
        dto.setChangeRate(object.get("changeRate").getAsBigDecimal());

        return dto;
    }
}
