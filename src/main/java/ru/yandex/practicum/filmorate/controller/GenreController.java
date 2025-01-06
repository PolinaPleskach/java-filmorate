package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.dto.genre.NewGenreRequest;
import ru.yandex.practicum.filmorate.dto.genre.UpdateGenreRequest;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    public GenreDto findGenre(@PathVariable("id") Long genreId) {
        log.info("Пришел GET запрос /genres с телом: {}.", genreId);
        GenreDto genre = genreService.findGenre(genreId);
        log.info("Отправлен ответ /genres с телом: {}.", genre);
        return genre;
    }

    @GetMapping
    public Collection<GenreDto> findAll() {
        log.info("Пришел GET запрос /genres.");
        Collection<GenreDto> genre = genreService.findAll();
        log.info("Пришел ответ /genres с телом: {}.", genre);
        return genre;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto create(@Valid @RequestBody NewGenreRequest genre) {
        log.info("Пришел POST запрос /genres с телом: {}", genre);
        GenreDto createdGenre = genreService.create(genre);
        log.info("Пришел ответ /genres с телом: {}", createdGenre);
        return createdGenre;
    }

    @PutMapping("/{id}")
    public GenreDto update(@Valid @RequestBody UpdateGenreRequest newGenre) {
        log.info("Пришел PUT запрос /genres с телом: {}", newGenre);
        GenreDto updateGenre = genreService.update(newGenre);
        log.info("Пришел ответ /genres с телом: {}", updateGenre);
        return updateGenre;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long mpaId) {
        log.info("Пришел DELETE запрос /genres с телом: {}", mpaId);
        boolean delete = genreService.delete(mpaId);
        log.info("Пришел ответ /genres с телом: {}", delete);
        return delete;
    }
}
