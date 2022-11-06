package ru.practicum.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.service.HitMapper;
import ru.practicum.stats.service.HitServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final HitMapper hitMapper;
    private final HitServiceImpl hitService;

    @PostMapping("/hit")
    public void create(@RequestBody EndpointHitDto endpointHitDto, HttpServletRequest request) {

        EndpointHit endpointHit = hitMapper.toHit(endpointHitDto);
        hitService.create(endpointHit);

        log.info("Запрос к эндпоинту '{}' на добавление статистики {}",
                request.getRequestURI(), endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> get(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                    pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                    pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam (defaultValue = "false") Boolean unique) {

        return hitMapper.toStats(hitService.getStats(start, end, uris, unique));

    }

}
