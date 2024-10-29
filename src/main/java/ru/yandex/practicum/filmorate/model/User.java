package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Integer id;
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

}
