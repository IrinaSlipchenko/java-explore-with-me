package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.Sort;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event createEvent(Event event);

    Event getByIdToUserId(Long userId, Long eventId);

    Event publish(Long eventId);

    Event reject(Long eventId);

    Event updateEventFromAdmin(Event event, Long eventId);

    Event updateEventFromCreator(Event event, Long userId);

    List<Event> getEventByParameters(List<Long> users, List<State> states,
                                     List<Long> categories,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                     Integer from, Integer size, Sort sort, Boolean onlyAvailable,
                                     Boolean paid, String text);

    Event findById(Long id);

    Event cancellation(Long userId, Long eventId);

    List<Event> getAllByUserId(Long userId, Integer from, Integer size);
}
