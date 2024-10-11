package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

@RestController
@RequestMapping({"/films"})
public class FilmController {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private static int generatedId = 0;
    private final Map<Integer, Film> films = new HashMap();

    public FilmController() {
    }

    @PostMapping
    public Film createFilm(@RequestBody @Valid Film film) {
        film.setId(++generatedId);
        this.films.put(film.getId(), film);
        log.debug("Фильм под название {} создан.", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        int id = film.getId();
        if (!this.films.containsKey(id)) {
            log.debug("Фильма с таким id {} не существует.", id);
            throw new ValidationException("Такого фильма не существует.");
        } else {
            this.films.put(id, film);
            log.debug("Фильм под название {} изменен.", film.getName());
            return film;
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList(this.films.values());
    }
}