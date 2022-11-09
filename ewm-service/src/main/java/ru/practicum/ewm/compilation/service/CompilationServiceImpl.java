package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.OffsetLimitPageable;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventFactory;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventFactory eventFactory;

    @Override
    public Compilation create(Compilation compilation) {
        return compilationRepository.save(compilation);
    }

    @Override
    public void delete(Long compId) {
        Compilation compilation = getById(compId);
        compilationRepository.delete(compilation);
    }

    @Override
    public void addEventInCompilation(Long compId, Long eventId) {
        Compilation compilation = getById(compId);

        Set<Event> events = compilation.getEvents();
        events.add(eventFactory.getById(eventId));
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Override
    public void delEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = getById(compId);

        Set<Event> events = compilation.getEvents();
        events.remove(eventFactory.getById(eventId));
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Override
    public void changePin(Long compId, Boolean pinned) {
        Compilation compilation = getById(compId);
        compilation.setPinned(pinned);
        compilationRepository.save(compilation);
    }

    @Override
    public Compilation getById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("compilation not found id= " + compId));
    }

    @Override
    public List<Compilation> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = OffsetLimitPageable.of(from, size);

        return compilationRepository.findAllByPinned(pinned, pageable);
    }
}
