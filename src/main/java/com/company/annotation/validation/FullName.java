package com.company.annotation.validation;

import com.company.annotation.processor.FullNameAnnotationProcessor;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.company.common.AppConstants.Regex.REGEX_FULL_NAME;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = FullNameAnnotationProcessor.class)
public @interface FullName {
    /**
     * default value {@code ^[A-Za-z]{2,}(?:\s[A-Za-z]+)*$}
     * ^ : Start of the string
     * [A-Za-z]{2,} : Match at least 2 alphabetical characters (uppercase or lowercase)
     * (?:\s[A-Za-z]+)* : Match zero or more occurrences of a space followed by one or more alphabetical characters. The * quantifier allows zero or more occurrences of the non-capturing group.
     * $ : End of the string
     * @return String containing the regex
     */
    String regex() default REGEX_FULL_NAME;

    String message() default "{com.company.annotation.validation.FullName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
