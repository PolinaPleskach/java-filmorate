package ru.yandex.practicum.filmorate.dto.genre;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateGenreRequest {
    @NotNull(message = "Название жанра является обязательным полем, заполните его.")
    Long id;
    String name;
}
