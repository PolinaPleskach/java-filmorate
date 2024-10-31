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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
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
    public void deleteFriend(@PathVariable("id") Long userId, @PathVariable("user-id") Long friendId) {
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
