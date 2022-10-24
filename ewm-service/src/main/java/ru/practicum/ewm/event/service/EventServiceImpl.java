package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ValidationException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventServiceImpl {
    private static final int MIN_HOURS_TO_EVENT = 2;

    private final EventRepository eventRepository;

    public Event create(Event event) {
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(MIN_HOURS_TO_EVENT);
        if (timeLimit.isBefore(event.getEventDate())) {
            return eventRepository.save(event);
        }
        throw new ValidationException("wrong event date");
    }
}
