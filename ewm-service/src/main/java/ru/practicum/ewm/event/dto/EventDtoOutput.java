package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoOutput {
    private Long id;
    private String title;
    private CategoryDto category;
    private UserShortDto initiator;
    private String annotation;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private LocalDateTime eventDate;
    private Location location;
    private Long participantLimit;
    private Boolean paid;
    private Boolean requestModeration;
    private State state;
    private Long views;

    // Количество одобренных заявок на участие в данном событии
    private Long confirmedRequests;


}
