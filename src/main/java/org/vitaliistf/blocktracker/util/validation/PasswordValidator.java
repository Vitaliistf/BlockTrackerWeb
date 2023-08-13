package org.vitaliistf.blocktracker.util.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
    private Pattern pattern;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return !password.isEmpty() && pattern.matcher(password).matches();
    }
}