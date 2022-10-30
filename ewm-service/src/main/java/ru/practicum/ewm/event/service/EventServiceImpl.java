package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private static final int MIN_HOURS_FROM_CREATE_TO_EVENT = 2;
    private static final int MIN_HOURS_FROM_PUBLISHED_TO_EVENT = 1;

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event createEvent(Event event) {
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(MIN_HOURS_FROM_CREATE_TO_EVENT);
        if (timeLimit.isBefore(event.getEventDate())) {
            event.setState(State.PENDING);
            return eventRepository.save(event);
        }
        throw new ValidationException("wrong event date");
    }

    @Override
    public Event getByIdToUserId(Long userId, Long eventId) {
        Event event = getById(eventId);

        if (userId.equals(event.getInitiator().getId())) {
            return event;
        }
        throw new NotFoundException("user Id not equal initiator Id");
    }

    @Override
    public Event publish(Long eventId) {
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(MIN_HOURS_FROM_PUBLISHED_TO_EVENT);
        Event event = getById(eventId);

        if (event.getState().equals(State.PENDING) && timeLimit.isBefore(event.getEventDate())) {
            event.setState(State.PUBLISHED);
            event.setPublished(LocalDateTime.now());
            return eventRepository.save(event);
        }
        throw new ValidationException("wrong state or wrong event date");
    }

    @Override
    public Event reject(Long eventId) {
        Event event = getById(eventId);

        if (event.getState().equals(State.PENDING)) {
            event.setState(State.CANCELED);
            return eventRepository.save(event);
        }
        throw new ValidationException("wrong state");
    }

    private Event getById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
    }

    @Override
    public Event updateEvent(Event event, Long eventId) {
        Event oldEvent = getById(eventId);
        eventMapper.updateEvent(event, oldEvent);

        return eventRepository.save(oldEvent);
    }

    @Override
    public List<Event> searchEvents(List<Long> users, List<State> states,
                                    List<Long> categories,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                    Integer from, Integer size) {
        return null;
    }

    @Override
    public Event findById(Long id) {
        Event event = getById(id);

        if (event.getState().equals(State.PUBLISHED)) {
            return event;
        }
        throw new ValidationException("wrong state or wrong event id");
    }


}
