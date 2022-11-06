package ru.practicum.ewm.request.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.event.service.EventFactory;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventFactory.class, UserService.class})
public interface RequestMapper {

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto toRequestDto(ParticipationRequest request);

    @Mapping(target = "requester", source = "userId")
    @Mapping(target = "event", source = "eventId")
    @Mapping(target = "status", constant = "PENDING")
    ParticipationRequest toRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> toRequestDto(List<ParticipationRequest> requests);
}
