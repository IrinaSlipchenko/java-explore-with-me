package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl {
    private final HitRepository hitRepository;

    public void create(EndpointHit endpointHit) {
        hitRepository.save(endpointHit);
    }

    public List<HitDto> getStats(LocalDateTime start, LocalDateTime end,
                                 List<String> uris, Boolean unique) {
        if(unique){
            return hitRepository.getUniqueHits(start, end, uris);
        }
        return hitRepository.getHits(start, end, uris);
    }
}
