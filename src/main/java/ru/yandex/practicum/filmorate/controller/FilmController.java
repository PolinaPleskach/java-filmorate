package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/{id}")
    public FilmDto findFilm(@PathVariable Long id) {
        return filmService.findFilm(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto create(@Valid @RequestBody NewFilmRequest film) {
        log.info("Пришел POST запрос /films с телом: {}", film);
        FilmDto createFilm = filmService.create(film);
        log.info("Отправлен ответ /films с телом: {}", createFilm);
        return createFilm;
    }

    @PutMapping
    public FilmDto update(@Valid @RequestBody UpdateFilmRequest newFilm) {
        log.info("Пришел PUT запрос /films с телом: {}", newFilm);
        FilmDto updatedFilm = filmService.update(newFilm);
        log.info("Отправлен ответ /films с телом: {}", updatedFilm);
        return updatedFilm;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long filmId) {
        log.info("Пришел DELETE запрос /films с телом: {}", filmId);
        boolean deleteFilm = filmService.delete(filmId);
        log.info("Отправлен ответ /films с телом: {}", deleteFilm);
        return deleteFilm;
    }

    @PutMapping("/{id}/like/{user-id}")
    public void addLike(@PathVariable("id") Long filmId, @PathVariable("user-id") Long userId) {
        log.info("Пришел PUT запрос /likes с телом: {}.", userId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{user-id}")
    public void deleteLike(@PathVariable("id") Long filmId, @PathVariable("user-id") Long userId) {
        log.info("Пришел DELETE запрос /likes с телом: {}.", userId);
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<FilmDto> findPopular(@RequestParam(name = "count", defaultValue = "10") Integer count) {
        log.info("Пришел GET запрос /popular с телом: {}.", count);
        Collection<FilmDto> findPopular = filmService.findPopular(count);
        log.info("Отправлен ответ /popular с телом: {}.", findPopular);
        return findPopular;
    }

    @GetMapping
    public Collection<FilmDto> findAllFilms() {
        log.info("Пришел GET запрос /films");
        Collection<FilmDto> findAllFilms = filmService.findAll();
        log.info("Отправлен ответ /films с телом: {}.", findAllFilms);
        return findAllFilms;
    }
}
