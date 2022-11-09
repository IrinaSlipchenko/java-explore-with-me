package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDtoInput;
import ru.practicum.ewm.compilation.dto.CompilationDtoOutput;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.service.CompilationMapper;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.exception.ValidationService;

import javax.validation.ConstraintViolationException;


@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationMapper compilationMapper;
    private final CompilationService compilationService;

    private final ValidationService validator;

    @PostMapping
    public CompilationDtoOutput create(@RequestBody CompilationDtoInput compilationDtoInput) {
        log.info("create compilation {}", compilationDtoInput);
        try {
            Compilation compilation = compilationMapper.toCompilation(validator.validateCompilation(compilationDtoInput));
            return compilationMapper.toDto(compilationService.create(compilation));
        } catch (ConstraintViolationException exception) {
            throw new ValidationException("title is blank ");
        }
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        log.info("delete compilation, id={}", compId);
        compilationService.delete(compId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventInCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId) {
        log.info("add event in compilation, compId={}, eventId={}", compId, eventId);
        compilationService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void delEventFromCompilation(@PathVariable Long compId,
                                        @PathVariable Long eventId) {
        log.info("del event from compilation, compId={}, eventId={}", compId, eventId);
        compilationService.delEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void attachedFromMain(@PathVariable Long compId) {
        log.info("attached from main, compId={}", compId);
        compilationService.changePin(compId, true);
    }

    @DeleteMapping("/{compId}/pin")
    public void releasedFromMain(@PathVariable Long compId) {
        log.info("released from main, compId={}", compId);
        compilationService.changePin(compId, false);
    }
}
