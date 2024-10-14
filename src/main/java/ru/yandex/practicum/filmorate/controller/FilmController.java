package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"/films"})
public class FilmController {
    @Generated
    private int generatedId = 0;
    private final Map<Integer, Film> films = new HashMap();

    public FilmController() {
    }

    @PostMapping
    public Film createFilm(@RequestBody @Valid Film film) {
        film.setId(++generatedId);
        this.films.put(film.getId(), film);
        log.info("Фильм под название {} создан.", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        int id = film.getId();
        if (!this.films.containsKey(id)) {
            log.info("Фильма с таким id {} не существует.", id);
            throw new ValidationException("Такого фильма не существует.");
        } else {
            Film updateFilm = films.get(film.getId());
            updateFilm.setName(film.getName());
            updateFilm.setDescription(film.getDescription());
            updateFilm.setDuration(film.getDuration());
            updateFilm.setReleaseDate(film.getReleaseDate());
            log.info("Фильм под названием {} обновлен", updateFilm.getName());
            return updateFilm;
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList(this.films.values());
    }
}