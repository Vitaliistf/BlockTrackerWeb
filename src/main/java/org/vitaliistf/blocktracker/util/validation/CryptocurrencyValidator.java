package org.vitaliistf.blocktracker.util.validation;

import org.vitaliistf.blocktracker.service.KuCoinApiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class CryptocurrencyValidator implements ConstraintValidator<ValidCryptocurrency, String> {

    private final KuCoinApiService kuCoinApiService;

    @Override
    public boolean isValid(String symbol, ConstraintValidatorContext context) {
        return kuCoinApiService.isCoinSupported(symbol);
    }
}

