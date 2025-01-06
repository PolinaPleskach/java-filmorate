package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;
import ru.yandex.practicum.filmorate.dto.mpa.NewMpaRequest;
import ru.yandex.practicum.filmorate.dto.mpa.UpdateMpaRequest;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/{id}")
    public MpaDto findMpa(@PathVariable("id") Long mpaId) {
        log.info("Пришел GET запрос /mpa с телом: {}.", mpaId);
        MpaDto mpa = mpaService.findMpa(mpaId);
        log.info("Отправлен ответ /mpa с телом: {}.", mpa);
        return mpa;
    }

    @GetMapping
    public Collection<MpaDto> findAll() {
        log.info("Пришел GET запрос /mpa.");
        Collection<MpaDto> mpa = mpaService.findAll();
        log.info("Отправлен ответ /mpa с телом: {}.", mpa);
        return mpa;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MpaDto create(@Valid @RequestBody NewMpaRequest mpa) {
        log.info("Пришел POST запрос /mpa с телом: {}", mpa);
        MpaDto createMpa = mpaService.create(mpa);
        log.info("Отправлен ответ /mpa с телом: {}.", createMpa);
        return createMpa;
    }

    @PutMapping("/{id}")
    public MpaDto update(@Valid @RequestBody UpdateMpaRequest newMpa) {
        log.info("Пришел PUT запрос /mpa с телом: {}", newMpa);
        MpaDto updateMpa = mpaService.update(newMpa);
        log.info("Отправлен ответ /mpa с телом: {}.", updateMpa);
        return updateMpa;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long mpaId) {
        log.info("Пришел DELETE запрос /mpa с телом: {}", mpaId);
        boolean delete = mpaService.delete(mpaId);
        log.info("Отправлен ответ /mpa с телом: {}.", delete);
        return delete;
    }
}
