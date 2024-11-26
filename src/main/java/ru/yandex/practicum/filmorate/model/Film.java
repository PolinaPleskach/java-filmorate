package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ReleaseDateOfFilm;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {
    Long id;
    @NotBlank(message = "Введите название фильма. Обратите внимание, что поле не может быть пустым.")
    String name;
    @Size(max = 200, message = "Размер не может превышать 200 символов.")
    String description;
    @ReleaseDateOfFilm(message = "Введите дату выхода фильма. Обратите внимание, что дата релиза не может быть " +
            "ранее 28 декабря 1895 года.")
    LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть больше 0.")
    Long duration;
    Set<Long> likes = new HashSet<>();
}