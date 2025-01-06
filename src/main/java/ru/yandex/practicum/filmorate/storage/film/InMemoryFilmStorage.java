package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicateDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("InMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private final Map<Long, LinkedHashSet<Long>> filmsGenresIds = new HashMap<>();
    private final Map<Long, Long> filmsMpaId = new HashMap<>();
    private Long generatorId;

    private long getNextId() {
        return ++generatorId;
    }

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film findFilm(Long filmId) {
        return Optional.ofNullable(films.get(filmId))
                .orElseThrow(() -> new NotFoundException(String.format("Фильм с id {} не найден", filmId)));
    }

    @Override
    public Collection<Film> findPopular(Integer count) {
        return findAll()
                .stream()
                .sorted(Comparator.comparing((Film film) -> film.getLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public void addLike(Film film, User user) {
        if (film.getLikes().contains(user.getId())) {
            throw new DuplicateDataException(String.format("Пользователь {} уже ставил лайк фильму {}.",
                    user.getName(), film.getName()));
        }
        film.getLikes().add(user.getId());
    }

    public LinkedHashSet<Long> getLikes(Film film) {
        LinkedHashSet<Long> likes = film.getLikes();
        if (likes == null || likes.isEmpty()) {
            return new LinkedHashSet<>();
        }
        return likes;
    }

    @Override
    public void deleteLike(Film film, User user) {
        if (!film.getLikes().contains(user.getId())) {
            throw new NotFoundException(String.format("Пользователь {} не ставил лайк фильму {}.",
                    user.getName(), film.getName()));
        }

        film.getLikes().remove(user.getId());
    }

    @Override
    public void addGenreId(Genre genre, Film film) {
        Optional<LinkedHashSet<Long>> filmGenresIds = Optional.ofNullable(filmsGenresIds.get(film.getId()));
        LinkedHashSet<Long> genresIds = new LinkedHashSet<>();
        if (filmGenresIds.isPresent()) {
            genresIds = filmGenresIds.get();
        }
        genresIds.add(genre.getId());
        filmsGenresIds.put(film.getId(), genresIds);
    }

    @Override
    public LinkedHashSet<Long> findGenresIds(Long filmId) {
        LinkedHashSet<Long> genresIds = filmsGenresIds.get(filmId);
        if (genresIds == null || genresIds.isEmpty()) {
            return new LinkedHashSet<>();
        }
        return genresIds;
    }

    @Override
    public Long findRatingId(Long filmId) {
        return Optional.ofNullable(filmsMpaId.get(filmId))
                .orElseThrow(() -> new NotFoundException(String.format("Рейтинг для фильма с ID {} не найден.", filmId)));
    }

    @Override
    public Film create(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        filmsMpaId.put(film.getId(), film.getMpa().getId());
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        films.put(newFilm.getId(), newFilm);
        filmsMpaId.put(newFilm.getId(), newFilm.getMpa().getId());
        log.info("Фильм под названием {} обновлен", newFilm.getName());
        return newFilm;
    }

    @Override
    public boolean delete(Long filmId) {
        films.remove(filmId);
        return Optional.ofNullable(films.get(filmId)).isPresent();
    }
}
