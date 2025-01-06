package ru.yandex.practicum.filmorate.dto.film;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.annotation.ReleaseDateOfFilm;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateFilmRequest {
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

    public boolean hasDescription() {
        return !StringUtils.isBlank(this.description);
    }

    public boolean hasReleaseDate() {
        return this.releaseDate != null;
    }

    public boolean hasDuration() {
        return this.duration != null;
    }
}
