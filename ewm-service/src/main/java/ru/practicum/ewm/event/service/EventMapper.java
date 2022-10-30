package ru.practicum.ewm.event.service;

import org.mapstruct.*;
import ru.practicum.ewm.category.service.CategoryFactory;
import ru.practicum.ewm.event.dto.EventDtoInput;
import ru.practicum.ewm.event.dto.EventDtoOutput;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryFactory.class, UserService.class})
public interface EventMapper {
    @Mapping(target = "category", source = "eventDtoInput.categoryId")
    @Mapping(target = "initiator", source = "userId")
    @Mapping(target = "lat", source = "eventDtoInput.location.lat")
    @Mapping(target = "lon", source = "eventDtoInput.location.lon")
    Event toEvent(EventDtoInput eventDtoInput, Long userId);

    @Mapping(target = "category", source = "eventDtoInput.categoryId")
    @Mapping(target = "lat", source = "eventDtoInput.location.lat")
    @Mapping(target = "lon", source = "eventDtoInput.location.lon")
    Event toEvent(EventDtoInput eventDtoInput);

    @Mapping(target = "createdOn", source = "created")
    @Mapping(target = "publishedOn", source = "published")
    @Mapping(target = "location.lat", source = "lat")
    @Mapping(target = "location.lon", source = "lon")
    EventDtoOutput toDto(Event event);

    List<EventDtoOutput> toDto(List<Event> events);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEvent(Event newEvent, @MappingTarget Event oldEvent);
}
