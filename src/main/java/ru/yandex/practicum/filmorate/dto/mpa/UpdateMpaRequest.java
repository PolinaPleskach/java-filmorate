package ru.yandex.practicum.filmorate.dto.mpa;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMpaRequest {
    Long id;
    @NotNull(message = "Название рейтинга является обязательным полем, заполните его.")
    String name;
    String description;

    public boolean hasDescription() {
        return !StringUtils.isBlank(this.description);
    }
}
