package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;


public class ValidateUserTest {
    private User user;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateUserEmailIsEmpty() {
        user = new User();
        user.setId(1);
        user.setEmail("");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(1994, 8, 10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная является обязательным полем, заполните его.", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserEmailIsRegexp() {
        user = new User();
        user.setId(1);
        user.setEmail("yandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(1994, 8, 10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта должна быть в формате user@ya.ru", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserLoginInSpaces() {
        user = new User();
        user.setId(1);
        user.setEmail("123@yandex.ru");
        user.setLogin("yan dex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(1994, 8, 10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может содержать пробелы.", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserLoginBirthDay() {
        user = new User();
        user.setId(1);
        user.setEmail("123@yandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.MAX);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Нельзя за дату рождения ставить время в будущем.", violations.iterator().next().getMessage());
    }
}