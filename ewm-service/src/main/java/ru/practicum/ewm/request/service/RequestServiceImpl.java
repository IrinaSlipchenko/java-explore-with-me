package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.request.repository.RequestRepository;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl {
    private final RequestRepository requestRepository;

}
