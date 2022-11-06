package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventDtoOutput;
import ru.practicum.ewm.event.dto.EventDtoOutputShort;
import ru.practicum.ewm.event.dto.Sort;
import ru.practicum.ewm.event.service.EventMapper;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.statistics.service.StatsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;
    private final StatsServiceImpl statsService;

    @GetMapping("/{id}")
    public EventDtoOutput getById(@PathVariable Long id, HttpServletRequest request) {

        statsService.setHits(request.getRequestURI(), request.getRemoteAddr());
        return eventMapper.toDto(eventService.findById(id));
    }

    @GetMapping
    public List<EventDtoOutputShort> getEvents(HttpServletRequest request,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) @DateTimeFormat(
                                                       iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                               LocalDateTime rangeStart,
                                               @RequestParam(required = false) @DateTimeFormat(
                                                       iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                                   LocalDateTime rangeEnd,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                               @Positive @RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam (required = false) Sort sort,
                                               @RequestParam (defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam (required = false) Boolean paid,
                                               @RequestParam (required = false) String text){
        return eventMapper.toDtoShort(
                eventService.getEventByParameters(null, null, categories, rangeStart, rangeEnd,
                        from, size, sort, onlyAvailable, paid, text));
    }
}
