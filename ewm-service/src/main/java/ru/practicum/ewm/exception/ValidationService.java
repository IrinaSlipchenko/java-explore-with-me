package ru.practicum.ewm.exception;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.compilation.dto.CompilationDtoInput;
import ru.practicum.ewm.user.dto.UserDto;

import javax.validation.Valid;

@Component
@Validated
public class ValidationService {
    public UserDto validateUser(@Valid UserDto userDto) {
        return userDto;
    }

    public CategoryDto validateCategory(@Valid CategoryDto categoryDto) {
        return categoryDto;
    }

    public CompilationDtoInput validateCompilation(@Valid CompilationDtoInput compilationDtoInput) {
        return compilationDtoInput;
    }
}
