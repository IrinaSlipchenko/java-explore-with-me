package ru.practicum.ewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDtoInput {
    @NotBlank(message = "title is blank")
    private String title;
    private Boolean pinned;  // Закреплена ли подборка на главной странице сайта
    private Set<Long> events;

}
