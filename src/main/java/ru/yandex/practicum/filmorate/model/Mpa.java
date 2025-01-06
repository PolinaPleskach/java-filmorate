package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
public class Mpa {
    private Long id;
    @NotNull(message = "Название рейтинга является обязательным полем, заполните его.")
    private String name;
    private String description;
}
