package ru.practicum.ewm.event.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.event.dto.EventDtoInput;
import ru.practicum.ewm.event.dto.EventDtoOutput;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.service.UserService;

@Mapper(componentModel = "spring", uses = {CategoryService.class, UserService.class})
public interface EventMapper {
    @Mapping(target = "category", source = "eventDtoInput.categoryId")
    @Mapping(target = "initiator", source = "userId")
    @Mapping(target = "lat", source = "eventDtoInput.location.lat")
    @Mapping(target = "lon", source = "eventDtoInput.location.lon")
    Event toEvent(EventDtoInput eventDtoInput, Long userId);

    @Mapping(target = "createdOn", source = "created")
    @Mapping(target = "publishedOn", source = "published")
    @Mapping(target = "location.lat", source = "lat")
    @Mapping(target = "location.lon", source = "lon")
    EventDtoOutput toDto(Event event);
}
