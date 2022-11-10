package ru.practicum.ewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.event.dto.EventDtoOutputShort;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDtoOutput {
    private Long id;
    private String title;
    private Boolean pinned;  // Закреплена ли подборка на главной странице

    private List<EventDtoOutputShort> events;
}
