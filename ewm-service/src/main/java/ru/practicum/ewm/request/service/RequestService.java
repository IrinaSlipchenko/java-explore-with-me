package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.model.ParticipationRequest;

import java.util.List;

public interface RequestService {
    ParticipationRequest create(ParticipationRequest request);

    List<ParticipationRequest> getRequestsByUser(Long userId);

    List<ParticipationRequest> getRequests(Long userId, Long eventId);

    ParticipationRequest cancelRequest(Long userId, Long requestId);

    ParticipationRequest confirmed(Long userId, Long eventId, Long reqId);

    ParticipationRequest rejected(Long userId, Long eventId, Long reqId);
}
