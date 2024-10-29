package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public Film create(Film film) {
        log.info("Пришел POST запрос /films с телом: {}.", film);
        film.setId(getNextId());
        this.films.put(film.getId(), film);
        log.info("Отправлен ответ /films с телом: {}.", film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Long id = film.getId();
        if (!this.films.containsKey(id)) {
            log.info("Фильма с таким id {} не существует.", id);
            throw new NotFoundException("Такого фильма не существует.");
        } else {
            this.films.put(id, film);
            log.info("Фильм под названием {} обновлен.", film.getName());
            return film;
        }
    }

    @Override
    public Optional<Film> findById(Long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }
}