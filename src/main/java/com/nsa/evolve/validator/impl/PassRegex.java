package com.nsa.evolve.validator.impl;

import com.nsa.evolve.validator.PassValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassRegex implements ConstraintValidator<PassValid, String> {

    private Pattern PASSWORD_REGEX;
    private Matcher matcher;

    @Override
    public void initialize(PassValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PASSWORD_REGEX = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}", Pattern.CASE_INSENSITIVE);
        matcher = PASSWORD_REGEX.matcher(value);

        if (matcher.matches()) return true;

        if (!Pattern.compile("(?=.*[a-z])").matcher(value).find())
            context.buildConstraintViolationWithTemplate("Password must contain 1 lower case character").addConstraintViolation();

        if (!Pattern.compile("(?=.*[A-Z])").matcher(value).find())
            context.buildConstraintViolationWithTemplate("Password must contain 1 upper case character").addConstraintViolation();

        if (!Pattern.compile("(?=.*[0-9])").matcher(value).find())
            context.buildConstraintViolationWithTemplate("Password must contain 1 number").addConstraintViolation();

        if (!Pattern.compile("(?=.{8,})").matcher(value).find())
            context.buildConstraintViolationWithTemplate("Password must contain 8 characters").addConstraintViolation();

        context.disableDefaultConstraintViolation();
        return false;
    }
}
