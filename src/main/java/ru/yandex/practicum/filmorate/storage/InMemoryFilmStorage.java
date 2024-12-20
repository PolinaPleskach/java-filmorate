package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long generatorId;

    private long getNextId() {
        return ++generatorId;
    }

    public Collection<Film> findAll() {
        return films.values();
    }

    public Optional<Film> findFilm(Long filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    public Film create(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film newFilm) {
        films.put(newFilm.getId(), newFilm);
        log.info("Фильм под названием {} обновлен", newFilm.getName());
        return newFilm;
    }
}
