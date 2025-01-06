package ru.yandex.practicum.filmorate.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.dto.genre.NewGenreRequest;
import ru.yandex.practicum.filmorate.dto.genre.UpdateGenreRequest;
import ru.yandex.practicum.filmorate.exception.DuplicateDataException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GenreService {
    GenreStorage genreStorage;

    @Autowired
    public GenreService(@Qualifier("GenreDbStorage"/*"InMemoryGenreStorage"*/) GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public GenreDto findGenre(Long genreId) {
        return GenreMapper.mapToGenreDto(genreStorage.findGenre(genreId));
    }

    public Collection<GenreDto> findAll() {
        return genreStorage.findAll().stream().map(GenreMapper::mapToGenreDto).collect(Collectors.toList());
    }

    public GenreDto create(NewGenreRequest request) {
        if (genreStorage.isGenreWithSameName(request.getName())) {
            throw new DuplicateDataException(String.format("Жанр с именем \"%s\" уже существует.", request.getName()));
        }
        Genre mpa = GenreMapper.mapToGenre(request);
        mpa = genreStorage.create(mpa);
        return GenreMapper.mapToGenreDto(mpa);
    }

    public GenreDto update(UpdateGenreRequest request) {
        if (request.getId() == null) {
            throw new ValidationException("Необходио указать id.");
        }
        Genre updatedGenre = GenreMapper.updateGenreFields(genreStorage.findGenre(request.getId()), request);
        updatedGenre = genreStorage.update(updatedGenre);

        return GenreMapper.mapToGenreDto(updatedGenre);
    }

    public boolean delete(Long genreId) {
        Genre genre = genreStorage.findGenre(genreId);
        return genreStorage.delete(genreId);
    }
}
