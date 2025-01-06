package ru.yandex.practicum.filmorate.dto.mpa;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewMpaRequest {
    @NotNull(message = "Название рейтинга является обязательным полем, заполните его.")
    String name;
    String description;
}
