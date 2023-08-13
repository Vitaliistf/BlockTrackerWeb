package org.vitaliistf.blocktracker.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WatchlistItemResponseDto {

    private Long id;

    private String symbol;

    private BigDecimal price;

    private BigDecimal changeRate;

    private BigDecimal changePrice;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal vol;

    private BigDecimal volValue;

}
