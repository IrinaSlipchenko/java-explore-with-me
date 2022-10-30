package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventDtoInput;
import ru.practicum.ewm.event.dto.EventDtoOutput;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventMapper;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestMapper;
import ru.practicum.ewm.request.service.RequestServiceImpl;

import javax.validation.Valid;
import java.util.List;

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

        Event event = eventMapper.toEvent(eventDtoInput, userId);
        return eventMapper.toDto(eventService.createEvent(event));
    }

    @GetMapping("/{eventId}")
    public EventDtoOutput getById(@PathVariable Long userId,
                                  @PathVariable Long eventId) {

        return eventMapper.toDto(eventService.getByIdToUserId(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable Long userId,
                                                           @PathVariable Long eventId) {
        return requestMapper.toRequestDto(requestService.getRequests(userId, eventId));
    }


}
