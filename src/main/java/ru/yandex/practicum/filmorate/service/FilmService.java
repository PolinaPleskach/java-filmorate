package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(Long id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Фильм не найден"));
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void addLike(Long filmId, Long userId) {
        Optional<Film> film = filmStorage.findById(filmId);
        Optional<User> user = userStorage.findById(userId);
        if (film.isEmpty()) {
            throw new NotFoundException("Не возможно поставить лайк на несуществующей фильм.");
        } else if (user.isEmpty()) {
            throw new NotFoundException("Не найден пользователь, чей устанавливается лайк.");
        } else if (findById(filmId).getLikes().contains(userId)) {
            throw new NotFoundException("Пользователь уже поставил лайк.");
        } else {
            findById(filmId).getLikes().add(userId);
        }
    }

    public void removeLike(Long filmId, Long userId) {
        Optional<Film> film = filmStorage.findById(filmId);
        Optional<User> user = userStorage.findById(userId);
        if (film.isEmpty()) {
            throw new NotFoundException("Не найден фильм, у которого удаляется лайк.");
        } else if (user.isEmpty()) {
            throw new NotFoundException("Не найден пользователь, чей удаляется лайк.");
        } else film.get().getLikes().remove(user.get().getId());

    }

    public List<Film> findPopular(int count) {

        if (count <= 0) {
            throw new ValidationException("Количество фильмов не может быть 0.");
        }

        return filmStorage.findAll().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(),film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}