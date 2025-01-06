package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody NewUserRequest user) {
        log.info("Пришел POST запрос /users с телом: {}", user);
        UserDto createUser = userService.createUser(user);
        log.info("Отправлен ответ /users с телом: {}", createUser);
        return createUser;
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UpdateUserRequest newUser) {
        log.info("Пришел PUT запрос /users с телом: {}", newUser);
        UserDto updatedUser = userService.updateUser(newUser);
        log.info("Отправлен ответ /users с телом: {}", updatedUser);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long userId) {
        log.info("Пришел DELETE запрос /users с телом: {}", userId);
        boolean deleteUser = userService.deleteUser(userId);
        log.info("Отправлен ответ /users с телом: {}", deleteUser);
        return deleteUser;
    }

    @GetMapping("/{id}")
    public UserDto findUser(@PathVariable("id") Long userId) {
        log.info("Пришел GET запрос /users", userId);
        UserDto findUser = userService.findUser(userId);
        log.info("Отправлен ответ /users с телом: {}", findUser);
        return findUser;
    }

    @GetMapping("/{id}/friends")
    public Collection<UserDto> findFriends(@PathVariable("id") Long userId) {
        log.info("Пришел GET запрос /friends с телом: {}", userId);
        Collection<UserDto> friends = userService.findFriends(userId);
        log.info("Отправлен ответ /friends с телом: {}", friends);
        return friends;
    }

    @GetMapping("/{id}/friends/common/{other-id}")
    public Collection<UserDto> getCommonFriends(@PathVariable("id") Long userId, @PathVariable("other-id") Long otherId) {
        log.info("Пришел GET запрос /friends с телом: {}, и {}", userId, otherId);
        Collection<UserDto> commonFriends = userService.getCommonFriends(userId, otherId);
        log.info("Отправлен ответ /friends с телом: {}", commonFriends);
        return commonFriends;
    }

    @GetMapping
    public Collection<UserDto> findAllUsers() {
        log.info("Пришел GET запрос /users.");
        Collection<UserDto> allUsers = userService.getAllUsers();
        log.info("Отправлен ответ /users с телом: {}", allUsers);
        return allUsers;
    }

    @PutMapping("/{id}/friends/{friend-id}")
    public void addFriend(@PathVariable("id") Long userId, @PathVariable("friend-id") Long friendId) {
        log.info("Пришел PUT запрос /friends с телом: {}, и {}", userId, friendId);
        userService.addFriend(userId, friendId);
        log.info("Отправлен ответ /friends ");
    }

    @DeleteMapping("/{id}/friends/{friend-id}")
    public void removeFriend(@PathVariable("id") Long userId, @PathVariable("friend-id") Long friendId) {
        log.info("Пришел DELETE запрос /friends с телом: {} и {}", userId, friendId);
        userService.removeFriend(userId, friendId);
        log.info("Отправлен ответ /friends ");
    }
}

