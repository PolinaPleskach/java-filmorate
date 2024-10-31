package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DuplicateDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User findUser(Long userId) {
        return userStorage.findUser(userId).orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
    }

    public User createUser(User user) {
        log.info("Создаем запись пользователя.");
        if (userStorage.isPreviouslyCreatedEmail(user.getEmail())) {
            throw new DuplicateDataException("Этот e-mail уже используется.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User updateUser(User newUser) {
        log.info("Обновляем данные пользователя");
        if (newUser.getId() == null) {
            throw new ValidationException("id пользователя должен быть указан");
        }
        Optional<User> oldUser = userStorage.findUser(newUser.getId());
        if (oldUser.isPresent()) {
            if (!userStorage.isPreviouslyCreatedEmail(newUser.getEmail())) {
                oldUser.get().setEmail(newUser.getEmail());
            }
            oldUser.get().setLogin(newUser.getLogin());
            if (!newUser.getName().isBlank()) {
                oldUser.get().setName(newUser.getName());
            }
            oldUser.get().setBirthday(newUser.getBirthday());
            return userStorage.update(oldUser.get());
        }
        throw new NotFoundException("Пользователь таким с id = " + newUser.getId() + " не найден.");
    }

    public void addFriend(Long userId, Long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);
        log.info("Добавляем {} в список друзей {}.", friend.getName(), user.getName());
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);
        log.info("Удаляем {} из списка друзей {}.", friend.getName(), user.getName());
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public Collection<User> findFriends(Long userId) {
        User user = findUser(userId);
        log.info("Список друзей {}.", user.getName());
        Set<Long> friendsIds = user.getFriends();
        if (friendsIds.isEmpty()) {
            log.info("Список друзей {} не содержит записей.", user.getName());
            return new ArrayList<>();
        }
        ArrayList<User> friendsList = new ArrayList<>();
        for (Long id : friendsIds) {
            friendsList.add(findUser(id));
        }
        return friendsList;
    }

    public Collection<User> findOther(Long userId, Long otherId) {
        log.info("Список пользователей");
        Collection<User> userFriends = findFriends(userId);
        Collection<User> otherUserFriends = findFriends(otherId);
        userFriends.retainAll(otherUserFriends);
        return userFriends;
    }

    public Collection<User> getAllUsers() {
        log.info("Получаем записи всех пользователей");
        return userStorage.getUsers();
    }
}
