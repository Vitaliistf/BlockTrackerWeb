package org.vitaliistf.blocktracker.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PortfolioRequestDto {
    @NotBlank(message = "Name of portfolio should not be empty.")
    private String name;
}
