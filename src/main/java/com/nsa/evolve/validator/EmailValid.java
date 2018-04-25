package com.nsa.evolve.validator;

import com.nsa.evolve.validator.impl.EmailRegex;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailRegex.class)
@Documented
public @interface EmailValid {

    String message() default "Email must be the correct format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
