package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventDtoInput;
import ru.practicum.ewm.event.dto.EventDtoOutput;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventMapper;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @PatchMapping("/{eventId}/publish")
    public EventDtoOutput publish(@PathVariable Long eventId) {

        return eventMapper.toDto(eventService.publish(eventId));
    }

    @PatchMapping("/{eventId}/reject")
    public EventDtoOutput reject(@PathVariable Long eventId) {

        return eventMapper.toDto(eventService.reject(eventId));
    }

    @PutMapping("/{eventId}")
    public EventDtoOutput update(@PathVariable Long eventId,
                                 @RequestBody EventDtoInput eventDtoInput) {
        Event event = eventMapper.toEvent(eventDtoInput);
        return eventMapper.toDto(eventService.updateEventFromAdmin(event, eventId));
    }

    @GetMapping
    public List<EventDtoOutput> searchEvent(@RequestParam(required = false) List<Long> users,
                                            @RequestParam(required = false) List<State> states,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) @DateTimeFormat(
                                                    iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                            LocalDateTime rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(
                                                    iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                            LocalDateTime rangeEnd,
                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                            @Positive @RequestParam(defaultValue = "10") Integer size) {

        return eventMapper.toDto(
                eventService.getEventByParameters(users, states, categories, rangeStart, rangeEnd, from, size,
                        null, null, null, null));
    }
}
