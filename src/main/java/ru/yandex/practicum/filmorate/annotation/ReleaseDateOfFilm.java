package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateDate.class)
public @interface ReleaseDateOfFilm {

    String message() default "Введите дату релиза не ранее {standartDate}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    String standardDate() default "1895-12-28";
}
