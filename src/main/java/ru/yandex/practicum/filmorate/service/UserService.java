package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(Long id) {
        return userStorage.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public User create(User user) {
        validateUser(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        validateUser(user);
        return userStorage.update(user);
    }

    public void addFriend(Long userId, Long friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь не найден");
        }
        findById(userId).getFriends().add(friendId);
        findById(friendId).getFriends().add(userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        findById(userId).getFriends().remove(friendId);
        findById(friendId).getFriends().remove(userId);
    }

    public List<User> findAllFriends(Long userId) {
        List<User> friendsList = new ArrayList<>();
        Set<Long> friends = findById(userId).getFriends();

        if (friends == null) {
            return friendsList;
        }

        for (Long friendId : friends) {
            friendsList.add(findById(friendId));
        }
        return friendsList;
    }

    public List<User> findCommonFriend(Long userId, Long friendId) {
        List<User> commonFriends = findAllFriends(userId);
        List<User> commonFriendsSecond = findAllFriends(friendId);
        commonFriends.retainAll(commonFriendsSecond);
        return commonFriends;
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}