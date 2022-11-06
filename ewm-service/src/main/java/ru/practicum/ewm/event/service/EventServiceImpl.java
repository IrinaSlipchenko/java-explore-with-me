package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.OffsetLimitPageable;
import ru.practicum.ewm.event.dto.Sort;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.request.dto.Status;
import ru.practicum.ewm.statistics.service.StatsServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private static final int MIN_HOURS_FROM_CREATE_TO_EVENT = 2;
    private static final int MIN_HOURS_FROM_PUBLISHED_TO_EVENT = 1;

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EntityManager entityManager;
    private final StatsServiceImpl statsService;

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
            setViews(event);
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
    public Event updateEventFromAdmin(Event event, Long eventId) {
        Event oldEvent = getById(eventId);
        eventMapper.updateEvent(event, oldEvent);

        return eventRepository.save(oldEvent);
    }

    @Override
    public Event updateEventFromCreator(Event event, Long userId) {
        Event oldEvent = getById(event.getId());

        if (userId.equals(oldEvent.getInitiator().getId())) {
            eventMapper.updateEvent(event, oldEvent);
            return eventRepository.save(oldEvent);
        }
        throw new ValidationException("wrong user id = " + userId);
    }

    @Override
    public List<Event> getEventByParameters(List<Long> users, List<State> states, List<Long> categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Integer from, Integer size,
                                            Sort sort, Boolean onlyAvailable, Boolean paid, String text) {

        List<Predicate> predicates = new ArrayList<>();
        List<Event> events;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteria = cb.createQuery(Event.class);
        Root<Event> eventRoot = criteria.from(Event.class);

        if (users != null && !users.isEmpty()) {
            predicates.add(cb.in(eventRoot.get("initiator").get("id")).value(users));
        }
        if (states != null && !states.isEmpty()) {
            predicates.add(cb.in(eventRoot.get("state")).value(states));
        }
        if (categories != null && !categories.isEmpty()) {
            predicates.add(cb.in(eventRoot.get("category").get("id")).value(categories));
        }
        if (rangeStart != null) {
            predicates.add(cb.greaterThan(eventRoot.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(cb.lessThan(eventRoot.get("eventDate"), rangeEnd));
        }
        if (text != null) {
            predicates.add(cb.or(
                    cb.like(cb.upper(eventRoot.get("annotation")), "%" + text.toUpperCase() + "%"),
                    cb.like(cb.upper(eventRoot.get("description")), "%" + text.toUpperCase() + "%")
            ));
        }
        if (paid != null) {
            predicates.add(cb.equal(eventRoot.get("paid"), paid));
        }

        if (sort == Sort.EVENT_DATE) {
            events = entityManager.createQuery(criteria.select(eventRoot)
                            .where(predicates.toArray(new Predicate[]{}))
                            .orderBy(cb.asc(eventRoot.get("eventDate"))))
                    .setFirstResult(from)
                    .setMaxResults(size)
                    .getResultList();
        } else {
            events = entityManager.createQuery(criteria.select(eventRoot)
                            .where(predicates.toArray(new Predicate[]{})))
                    .setFirstResult(from)
                    .setMaxResults(size)
                    .getResultList();
        }

        if (onlyAvailable != null && onlyAvailable) {
            return events.stream()
                    .filter(event -> {
                        long count = event.getRequests().stream()
                                .filter(request -> request.getStatus().equals(Status.CONFIRMED))
                                .count();
                        return event.getParticipantLimit() > count;
                    })
                    .collect(Collectors.toList());
        }
        return events;
    }

    @Override
    public Event findById(Long id) {
        Event event = getById(id);

        if (event.getState().equals(State.PUBLISHED)) {
            setViews(event);
            return event;
        }
        throw new ValidationException("wrong state or wrong event id");
    }

    @Override
    public Event cancellation(Long userId, Long eventId) {
        Event event = getById(eventId);

        if (event.getInitiator().getId().equals(userId) && event.getState().equals(State.PENDING)) {
            event.setState(State.CANCELED);
            return eventRepository.save(event);
        }
        throw new ValidationException("wrong state or wrong user id");
    }

    @Override
    public List<Event> getAllByUserId(Long userId, Integer from, Integer size) {
        Pageable pageable = OffsetLimitPageable.of(from, size);

        return eventRepository.findAllByInitiatorId(userId, pageable);
    }

    private void setViews(Event event) {
        Long hits = statsService.getHits(event.getId());
        event.setViews(hits);
    }
}
