package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"/users"})
public class UserController {
    @Generated
    private int generatedId = 0;
    private final Map<Integer, User> users = new HashMap();

    public UserController() {
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        this.validateUser(user);
        log.info("Пришел POST запрос /users с телом: {}", user);
        user.setId(++generatedId);
        this.users.put(user.getId(), user);
        log.info("Отправлен ответ /users с телом: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        int id = user.getId();
        if (!this.users.containsKey(id)) {
            log.info("Такого пользователя {} не существует.", user);
            throw new ValidationException("Такого пользователя не существует");
        } else {
            this.validateUser(user);
            this.users.put(id, user);
            log.info("Информация о пользователе {} обновлена.", user);
            return user;
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList(this.users.values());
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

    }
}