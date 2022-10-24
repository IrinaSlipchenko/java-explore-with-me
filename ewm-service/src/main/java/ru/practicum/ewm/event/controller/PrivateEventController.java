package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventDtoInput;
import ru.practicum.ewm.event.dto.EventDtoOutput;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventMapper;
import ru.practicum.ewm.event.service.EventServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventMapper eventMapper;
    private final EventServiceImpl eventService;


    @PostMapping
    public EventDtoOutput create(@PathVariable Long userId,
                                 @Valid @RequestBody EventDtoInput eventDtoInput) {

        Event event = eventMapper.toEvent(eventDtoInput, userId);
        return eventMapper.toDto(eventService.create(event));
    }


}
