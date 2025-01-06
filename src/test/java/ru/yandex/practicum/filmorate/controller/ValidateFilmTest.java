package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateFilmTest {
    private Film film;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateFilmName() {
        film = new Film();
        film.setId(1L);
        film.setName("");
        film.setDescription("Описание вашего фильма");
        film.setReleaseDate(LocalDate.of(2024,12,5));
        film.setDuration(120L);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Введите название фильма. Обратите внимание, что поле не может быть пустым.", violations.iterator().next().getMessage());
    }

    @Test
    void validateFilmReleaseDate() {
        film = new Film();
        film.setId(1L);
        film.setName("Название фильма");
        film.setDescription("Описание вашего фильма");
        film.setReleaseDate(LocalDate.of(1895,12,27));
        film.setDuration(120L);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Введите дату выхода фильма. Обратите внимание, что дата релиза не может быть ранее 28 декабря 1895 года.", violations.iterator().next().getMessage());
    }

    @Test
    void validateFilmDuration() {
        film = new Film();
        film.setId(1L);
        film.setName("Название фильма");
        film.setDescription("");
        film.setReleaseDate(LocalDate.of(2024,12,5));
        film.setDuration((long) -100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Продолжительность фильма должна быть больше 0.", violations.iterator().next().getMessage());
    }
}
