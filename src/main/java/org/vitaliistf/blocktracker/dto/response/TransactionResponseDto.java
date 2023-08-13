package org.vitaliistf.blocktracker.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponseDto {
    private Long id;
    private String symbol;
    private BigDecimal amount;
    private BigDecimal price;
    private boolean isBuy;
    private String date;
}