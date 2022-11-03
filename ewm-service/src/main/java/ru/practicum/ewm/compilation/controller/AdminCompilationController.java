package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDtoInput;
import ru.practicum.ewm.compilation.dto.CompilationDtoOutput;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.service.CompilationMapper;
import ru.practicum.ewm.compilation.service.CompilationServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationMapper compilationMapper;
    private final CompilationServiceImpl compilationService;

    @PostMapping
    public CompilationDtoOutput create(@Valid @RequestBody CompilationDtoInput compilationDtoInput) {
        Compilation compilation = compilationMapper.toCompilation(compilationDtoInput);

        return compilationMapper.toDto(compilationService.create(compilation));
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        compilationService.delete(compId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventInCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId) {

        compilationService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void delEventFromCompilation(@PathVariable Long compId,
                                        @PathVariable Long eventId) {

        compilationService.delEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void attachedFromMain(@PathVariable Long compId) {

        compilationService.attachedFromMain(compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void releasedFromMain(@PathVariable Long compId) {

        compilationService.releasedFromMain(compId);
    }

}
