package org.vitaliistf.blocktracker.util.validation;

import org.vitaliistf.blocktracker.models.User;

import org.vitaliistf.blocktracker.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UserValidator implements Validator {

    private final UserDetailsService userDetailsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (userDetailsService.emailExists(user.getEmail())) {
            errors.rejectValue("email", "", "User with such email is already registered.");
        }
    }
}
