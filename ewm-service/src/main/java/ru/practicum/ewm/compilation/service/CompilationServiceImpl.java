package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventFactory;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl {

    private final CompilationRepository compilationRepository;
    private final EventFactory eventFactory;

    public Compilation create(Compilation compilation) {
        return compilationRepository.save(compilation);
    }

    public void delete(Long compId) {
        compilationRepository.deleteById(compId);
    }

    public void addEventInCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation not found"));

        Set<Event> events = compilation.getEvents();
        events.add(eventFactory.getById(eventId));
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    public void pinCompilation(Long compId) {


    }
}
