package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.Pair;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.exception.DuplicateDataException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier(/*"InMemoryUserStorage"*/"UserDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserDto findUser(Long userId) {
        return UserMapper.mapToUserDto(userStorage.findUser(userId));
    }

    public UserDto createUser(NewUserRequest request) {
        if (userStorage.isPreviouslyCreatedEmail(request.getEmail())) {
            throw new DuplicateDataException("Пользователь с таким e-mail уже существует.");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            request.setName(request.getLogin());
        }
        User user = UserMapper.mapToUser(request);
        user = userStorage.create(user);
        return UserMapper.mapToUserDto(user);
    }

    public UserDto updateUser(UpdateUserRequest request) {
        if (request.getId() == null) {
            throw new ValidationException("Необходимо указать id.");
        }
        User updatedUser = UserMapper.updateUserFields(userStorage.findUser(request.getId()), request);
        updatedUser = userStorage.update(updatedUser);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public boolean deleteUser(Long filmId) {
        User user = userStorage.findUser(filmId);
        return userStorage.delete(filmId);
    }

    public void addFriend(Long userId, Long friendId) {
        Pair<String, String> names = userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        Pair<String, String> names = userStorage.deleteFriend(userId, friendId);
    }

    public Collection<UserDto> findFriends(Long userId) {
        Collection<User> friendsList = userStorage.findFriends(userId);
        if (friendsList.isEmpty()) {
            return new ArrayList<>();
        }
        return friendsList.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    public Collection<UserDto> getCommonFriends(Long userId, Long otherId) {
        Collection<UserDto> userFriends = findFriends(userId);
        Collection<UserDto> otherUserFriends = findFriends(otherId);
        userFriends.retainAll(otherUserFriends);
        return userFriends;
    }

    public Collection<UserDto> getAllUsers() {
        return userStorage.getUsers().stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }
}
