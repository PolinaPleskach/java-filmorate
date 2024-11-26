package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long generatorId;

    private long getNextId() {
        return ++generatorId;
    }

    public User create(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    public User update(User newUser) {
        log.info("Информация о пользователе {} обновлена.", newUser);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public boolean isPreviouslyCreatedEmail(String eMail) {
        return users.values().stream().anyMatch(userFromMemory -> userFromMemory.getEmail().equals(eMail));
    }

    public Optional<User> findUser(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
