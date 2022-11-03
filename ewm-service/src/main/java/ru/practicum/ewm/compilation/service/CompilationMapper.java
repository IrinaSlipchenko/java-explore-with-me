package ru.practicum.ewm.compilation.service;

import org.mapstruct.Mapper;
import ru.practicum.ewm.compilation.dto.CompilationDtoInput;
import ru.practicum.ewm.compilation.dto.CompilationDtoOutput;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.service.EventFactory;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventFactory.class})
public interface CompilationMapper {
    Compilation toCompilation(CompilationDtoInput compilationDtoInput);


    CompilationDtoOutput toDto(Compilation compilation);

    List<CompilationDtoOutput> toDto(List<Compilation> compilation);
}
