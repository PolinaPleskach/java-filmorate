package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@RestController
@RequestMapping({"/users"})
public class UserController {
    @Generated
    private  int generatedId = 0;
    private final Map<Integer, User> users = new HashMap();

    public UserController() {
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        this.validateUser(user);
        user.setId(++generatedId);
        this.users.put(user.getId(), user);
        log.info("Пользователь {} создан", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        int id = user.getId();
        if (!this.users.containsKey(id)) {
            log.debug("Такого пользователя {} не существует.", user);
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