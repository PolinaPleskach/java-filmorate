package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;
    @NotBlank(message = "Электронная является обязательным полем, заполните его.")
    @Email(message = "Электронная почта должна быть в формате user@ya.ru")
    private String email;
    @NotBlank(message = "Логин является обязательным полем, заполните его.")
    @Pattern(regexp = "^\\S*$", message = "Логин не может содержать пробелы.")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent(message = "Нельзя за дату рождения ставить время в будущем.")
    private LocalDate birthday;
    Set<Long> friends = new HashSet<>();
}
