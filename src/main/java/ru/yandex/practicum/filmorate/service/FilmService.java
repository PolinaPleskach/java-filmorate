package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DuplicateDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film findFilm(Long filmId) {
        return filmStorage.findFilm(filmId).orElseThrow(() -> new NotFoundException("Фильм с ID " + filmId + " не найден"));
    }

    public void addLike(Long filmId, Long userId) {
        Optional<Film> film = filmStorage.findFilm(filmId);
        Optional<User> user = userStorage.findUser(userId);

        if (film.isEmpty()) {
            throw new NotFoundException("Фильма с таким id " + filmId + " не существует. Лайк не проставлен.");
        } else if (user.isEmpty()) {
            throw new NotFoundException("Пользователя с таким id " + userId + " не существует. Лайк не проставлен.");
        } else if (film.get().getLikes().contains(userId)) {
            throw new DuplicateDataException("Ранее пользователь " + user.get().getName()
                    + " уже ставил лайк фильму " + film.get().getName());
        }

        log.info("Пользователь {} ставит лайк фильму {}", user.get().getName(), film.get().getName());
        film.get().getLikes().add(userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        Optional<Film> film = filmStorage.findFilm(filmId);
        Optional<User> user = userStorage.findUser(userId);

        if (film.isEmpty()) {
            throw new NotFoundException("Фильма с таким id " + filmId + " не существует. Лайк не был удален.");
        } else if (user.isEmpty()) {
            throw new NotFoundException("Пользователя с таким id " + userId + " существует. Лайк не был удален.");
        } else if (!film.get().getLikes().contains(userId)) {
            throw new DuplicateDataException("Ранее пользователь " + user.get().getName()
                    + "не ставил лайк фильму " + film.get().getName() + "." +
                    "невозможно удалить лайк.");
        }

        log.info("Пользователь {} убирает лайк с фильма {}", user.get().getName(), film.get().getName());
        film.get().getLikes().remove(userId);
    }

    public Collection<Film> findPopular(Integer count) {
        if (count <= 0) {
            throw new ValidationException("Количество фильмов должно быть больше 0");
        }

        return filmStorage.findAll()
                .stream()
                .sorted(Comparator.comparing((Film film) -> film.getLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Collection<Film> findAllFilms() {
        return filmStorage.findAll();
    }

    public Film createFilm(Film film) {
        log.info("Создаем запись о фильме {}", film.getName());
        return filmStorage.create(film);
    }

    public Film updateFilm(Film newFilm) {
        log.info("Обновляем данные фильма");
        if (newFilm.getId() == null) {
            throw new ValidationException("Не указан Id фильма!");
        }
        Optional<Film> oldFilm = filmStorage.findFilm(newFilm.getId());
        if (oldFilm.isPresent()) {
            return filmStorage.update(newFilm);
        }
        throw new NotFoundException("Фильма с  таким id = " + newFilm.getId() + " под названием \"" + newFilm.getName() + "\" не существует");
    }
}
