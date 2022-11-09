package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.service.RequestMapper;
import ru.practicum.ewm.request.service.RequestServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestMapper requestMapper;

    private final RequestServiceImpl requestService;

    // Может ли пользователь отменить заявку на участие в событии,
    // которую уже подтвердил инициатор?

    @PostMapping
    public ParticipationRequestDto create(@PathVariable Long userId,
                                          @RequestParam("eventId") Long eventId) {
        log.info("Creating participation request userId={}, eventId={}", userId, eventId);
        ParticipationRequest request = requestMapper.toRequest(userId, eventId);
        return requestMapper.toRequestDto(requestService.create(request));
    }

    @GetMapping
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable Long userId) {

        return requestMapper.toRequestDto(requestService.getRequestsByUser(userId));
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {

        return requestMapper.toRequestDto(requestService.cancelRequest(userId, requestId));
    }
}
