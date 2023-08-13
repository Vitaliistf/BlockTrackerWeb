package org.vitaliistf.blocktracker.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinResponseDto {
    private Long id;
    private String symbol;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal avgBuyingPrice;
}
