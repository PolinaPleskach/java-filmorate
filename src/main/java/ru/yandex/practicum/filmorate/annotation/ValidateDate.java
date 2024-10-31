package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidateDate implements ConstraintValidator<ReleaseDateOfFilm, LocalDate> {
    private LocalDate min = LocalDate.MIN;

    @Override
    public void initialize(ReleaseDateOfFilm constraintAnnotation) {
        this.min = LocalDate.parse(constraintAnnotation.standartDate());
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        return localDate.isAfter(min);
    }
}