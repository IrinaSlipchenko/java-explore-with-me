package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.repository.CompilationRepository;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl {

    private final CompilationRepository compilationRepository;

}
