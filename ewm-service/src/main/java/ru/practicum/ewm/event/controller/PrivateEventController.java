package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventDtoInput;
import ru.practicum.ewm.event.dto.EventDtoInputOnUpdate;
import ru.practicum.ewm.event.dto.EventDtoOutput;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventMapper;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestMapper;
import ru.practicum.ewm.request.service.RequestServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;
    private final RequestMapper requestMapper;
    private final RequestServiceImpl requestService;


    @PostMapping
    public EventDtoOutput create(@PathVariable Long userId,
                                 @Valid @RequestBody EventDtoInput eventDtoInput) {
        log.info("create new event userId={}, eventDtoInput={}", userId, eventDtoInput);
        Event event = eventMapper.toEvent(eventDtoInput, userId);
        return eventMapper.toDto(eventService.createEvent(event));
    }

    @PatchMapping
    public EventDtoOutput update(@PathVariable Long userId,
                                 @Valid @RequestBody EventDtoInputOnUpdate eventDtoInput) {

        log.info("update event userId={}, eventDtoInput={}", userId, eventDtoInput);
        Event event = eventMapper.toEvent(eventDtoInput);
        return eventMapper.toDto(eventService.updateEventFromCreator(event, userId));
    }

    @GetMapping
    public List<EventDtoOutput> getAllByUserId(@PathVariable Long userId,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {

        log.info("get all event by userId,  userId={}, from={}, size={}", userId, from, size);
        return eventMapper.toDto(eventService.getAllByUserId(userId, from, size));
    }

    @GetMapping("/{eventId}")
    public EventDtoOutput getById(@PathVariable Long userId,
                                  @PathVariable Long eventId) {

        log.info("get event by id, userId={}, eventId={}", userId, eventId);
        return eventMapper.toDto(eventService.getByIdToUserId(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public EventDtoOutput cancellation(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        log.info("cancel event by id, userId={}, eventId={}", userId, eventId);
        return eventMapper.toDto(eventService.cancellation(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable Long userId,
                                                           @PathVariable Long eventId) {

        log.info("get requests by user, userId={}, eventId={}", userId, eventId);
        return requestMapper.toRequestDto(requestService.getRequests(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmed(@PathVariable Long userId,
                                             @PathVariable Long eventId,
                                             @PathVariable Long reqId) {

        log.info("confirm someone else's request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return requestMapper.toRequestDto(requestService.confirmed(userId, eventId, reqId));
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejected(@PathVariable Long userId,
                                            @PathVariable Long eventId,
                                            @PathVariable Long reqId) {

        log.info("reject someone else's request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return requestMapper.toRequestDto(requestService.rejected(userId, eventId, reqId));
    }
}
