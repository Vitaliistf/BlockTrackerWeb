package org.vitaliistf.blocktracker.dto.request;

import org.vitaliistf.blocktracker.util.validation.ValidCryptocurrency;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WatchlistItemRequestDto {

    @NotBlank(message = "Symbol cannot be empty.")
    @ValidCryptocurrency
    private String symbol;

}
