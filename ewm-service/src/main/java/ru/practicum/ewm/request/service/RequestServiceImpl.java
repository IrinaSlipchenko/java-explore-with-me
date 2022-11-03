package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.request.dto.Status;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventService eventService;

    public ParticipationRequest create(ParticipationRequest request) {

        if (!request.getEvent().getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event must be published", "event state = " + request.getEvent().getState());
        }
        if (request.getRequester().equals(request.getEvent().getInitiator())) {
            throw new ConflictException("Initiator can not be requester", "user id = " + request.getRequester().getId());
        }

        requestRepository.findByRequesterAndEvent(request.getRequester(), request.getEvent())
                .ifPresent((r) -> {
                    throw new ConflictException("Request already exist", "Request id = " + r.getId());
                });
        Long requestCount = requestRepository.countByEventAndStatus(request.getEvent(), Status.CONFIRMED);
        if (requestCount >= request.getEvent().getParticipantLimit()) {
            throw new ConflictException("Event participant limit exceeded", "request count" + requestCount);
        }
        if (!request.getEvent().getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        }
        return requestRepository.save(request);
    }

    public List<ParticipationRequest> getRequestsByUser(Long userId) {
        User user = userService.getById(userId);

        return requestRepository.findAllByRequester(user);
    }

    public List<ParticipationRequest> getRequests(Long userId, Long eventId) {
        Event event = eventService.findById(eventId);
        if (event.getInitiator().getId().equals(userId)) {
            return requestRepository.findAllByEvent(event);
        }
        throw new ValidationException("only the initiator can view information" +
                "about requests to participate in the event");
    }

    public ParticipationRequest cancelRequest(Long userId, Long requestId) {
        ParticipationRequest request = getById(requestId);

        if (request.getRequester().getId().equals(userId)) {
            request.setStatus(Status.CANCELED);
            return requestRepository.save(request);
        }
        throw new ConflictException("request can only be canceled by its creator", "");
    }

    public ParticipationRequest confirmed(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = getById(reqId);
        Event event = request.getEvent();

        if(event.getId().equals(eventId) && event.getInitiator().getId().equals(userId) &&
        event.getRequestModeration() && event.getParticipantLimit() > 0){
            request.setStatus(Status.CONFIRMED);
            return requestRepository.save(request);
        }
        throw new ConflictException("the request has the right to confirm the event creator", "");
    }

    public ParticipationRequest rejected(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = getById(reqId);
        Event event = request.getEvent();

        if(event.getId().equals(eventId) && event.getInitiator().getId().equals(userId)){
            request.setStatus(Status.REJECTED);
            return requestRepository.save(request);
        }
        throw new ConflictException("the request has the right to reject the event creator", "");
    }

    private ParticipationRequest getById(Long requestId){
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("request not exist id= " + requestId));
    }
}
