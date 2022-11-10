package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.model.Compilation;

import java.util.List;

public interface CompilationService {
    Compilation create(Compilation compilation);

    void delete(Long compId);

    void addEventInCompilation(Long compId, Long eventId);

    void delEventFromCompilation(Long compId, Long eventId);

    void changePin(Long compId, Boolean pinned);

    Compilation getById(Long compId);

    List<Compilation> getCompilations(Boolean pinned, Integer from, Integer size);
}
