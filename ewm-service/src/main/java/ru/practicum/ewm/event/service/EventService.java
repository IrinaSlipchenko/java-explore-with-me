package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event createEvent(Event event);

    Event getByIdToUserId(Long userId, Long eventId);

    Event publish(Long eventId);

    Event reject(Long eventId);

    Event updateEvent(Event event, Long eventId);

    List<Event> searchEvents(List<Long> users, List<State> states,
                             List<Long> categories,
                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                             Integer from, Integer size);

    Event findById(Long id);
}
