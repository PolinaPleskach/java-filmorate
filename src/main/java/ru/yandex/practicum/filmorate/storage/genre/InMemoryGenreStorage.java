package ru.yandex.practicum.filmorate.storage.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component("InMemoryGenreStorage")
public class InMemoryGenreStorage implements GenreStorage {
    private Map<Long, Genre> genres = new HashMap<>();
    private Long generatorId;

    private long getNextId() {
        return ++generatorId;
    }

    public InMemoryGenreStorage() {
        genres.put(1L, new Genre(1L, "Комедия"));
        genres.put(2L, new Genre(2L, "Драма"));
        genres.put(3L, new Genre(3L, "Мультфильм"));
        genres.put(4L, new Genre(4L, "Триллер"));
        genres.put(5L, new Genre(5L, "Документальный"));
        genres.put(6L, new Genre(6L, "Боевик"));
    }

    @Override
    public boolean isGenreWithSameName(String name) {
        return genres.values().stream().anyMatch(genreFromMemory -> genreFromMemory.getName().equals(name));
    }

    @Override
    public Collection<Genre> findAll() {
        return genres.values();
    }

    @Override
    public Genre findGenre(Genre genre) {
        return Optional.ofNullable(genres.get(genre.getId()))
                .orElseThrow(() -> new GenreNotFoundException(String.format("В запросе не корректный жанр с ID %d", genre.getId())));
    }

    @Override
    public Genre findGenre(Long genreId) {
        return Optional.ofNullable(genres.get(genreId))
                .orElseThrow(() -> new NotFoundException(String.format("Жанр с ID {} не найден", genreId)));
    }

    @Override
    public Genre create(Genre genre) {
        genre.setId(getNextId());
        genres.put(genre.getId(), genre);
        return genre;
    }

    @Override
    public Genre update(Genre newGenre) {
        genres.put(newGenre.getId(), newGenre);
        return newGenre;
    }

    @Override
    public boolean delete(Long genreId) {
        genres.remove(genreId);
        return Optional.ofNullable(genres.get(genreId)).isPresent();
    }
}
