package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoInput {
    @NotNull
    @Size(min = 3, max = 120)
    private String title;             // 3 <= Length <= 120
    @JsonProperty("category")
    private Long categoryId;         // id категории к которой относится событие
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;       // Краткое описание события 20 <= Length <= 2000
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;      // Полное описание события 20 <= Length <= 7000
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate; // в формате "yyyy-MM-dd HH:mm:ss", на которые намечено событие
    private Location location;       // Широта и долгота места проведения события
    private Long participantLimit;   // Ограничение на количество участников.
    // Значение 0 - означает отсутствие ограничения
    private Boolean paid;            // Нужно ли оплачивать участие в событии
    private Boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    // false - будут подтверждаться автоматически
    // true - все заявки будут ожидать подтверждения инициатором события
}
