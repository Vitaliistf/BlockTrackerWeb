package org.vitaliistf.blocktracker.dto.request;

import org.vitaliistf.blocktracker.util.validation.ValidCryptocurrency;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransactionRequestDto {

    @NotBlank(message = "Symbol cannot be empty.")
    @ValidCryptocurrency
    private String symbol;

    @NotNull(message = "Amount cannot be empty.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount cannot be less than 0.")
    private BigDecimal amount;

    @NotNull(message = "Price cannot be empty.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price cannot be less than 0.")
    private BigDecimal price;

    private boolean isBuy;

    private Long coinId;

    private Long portfolioId;

}
