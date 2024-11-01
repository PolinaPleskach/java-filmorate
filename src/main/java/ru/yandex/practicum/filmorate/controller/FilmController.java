package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/{id}")
    public Film findFilm(@PathVariable long id) {
        return filmService.findFilm(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Пришел POST запрос /films с телом: {}", film);
        Film createFilm = filmService.createFilm(film);
        log.info("Отправлен ответ /films с телом: {}", film);
        return createFilm;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        return filmService.updateFilm(newFilm);
    }

    @PutMapping("/{id}/like/{user-id}")
    public void addLike(@PathVariable("id") Long filmId, @PathVariable("user-id") Long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{user-id}")
    public void deleteLike(@PathVariable("id") Long userId, @PathVariable("user-id") Long friendId) {
        filmService.deleteLike(userId, friendId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopular(@RequestParam(name = "count", defaultValue = "10") Integer count) {
        return filmService.findPopular(count);
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        return filmService.findAllFilms();
    }
}
