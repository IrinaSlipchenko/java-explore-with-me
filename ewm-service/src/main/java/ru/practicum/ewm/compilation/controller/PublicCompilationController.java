package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDtoOutput;
import ru.practicum.ewm.compilation.service.CompilationMapper;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationMapper compilationMapper;
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDtoOutput> getCompilations(@Valid
                                                      @RequestParam(name = "pinned", required = false, defaultValue = "true") Boolean pinned,
                                                      @PositiveOrZero @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        log.info("get compilations pinned={}, from={}, size={}", pinned, from, size);
        return compilationMapper.toDto(compilationService.getCompilations(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public CompilationDtoOutput getById(@PathVariable Long compId) {
        log.info("get compilation by id={}", compId);
        return compilationMapper.toDto(compilationService.getById(compId));
    }
}
