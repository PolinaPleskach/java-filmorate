package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final String userFriendsPath = "/{id}/friends/{friend-id}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        log.info("Пришел POST запрос /users с телом: {}", user);
        User createUser = userService.createUser(user);
        log.info("Отправлен ответ /users с телом: {}", user);
        return createUser;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        return userService.updateUser(newUser);
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable("id") Long userId) {
        return userService.findUser(userId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> findFriends(@PathVariable("id") Long userId) {
        return userService.findFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{other-id}")
    public Collection<User> getCommonFriends(@PathVariable("id") Long userId, @PathVariable("other-id") Long otherId) {
        return userService.findOther(userId, otherId);
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping(userFriendsPath)
    public void addFriend(@PathVariable("id") Long userId, @PathVariable("friend-id") Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping(userFriendsPath)
    public void removeFriend(@PathVariable("id") Long userId, @PathVariable("friend-id") Long friendId) {
        userService.removeFriend(userId, friendId);
    }
}
