package ru.practicum.ewm.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.statistics.client.StatsClient;
import ru.practicum.ewm.statistics.dto.EndpointHitDto;
import ru.practicum.ewm.statistics.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl {
    private static final String APP_NAME = "ewm-service";
    private final StatsClient client;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setHits(String uri, String ip) {

        EndpointHitDto endpointHit1 = new EndpointHitDto(null, APP_NAME, uri, ip, LocalDateTime.now());

        log.info("Отправляем запрос на сервер статистики для {}", uri);

        EndpointHitDto endpointHit2 = client.setStat(endpointHit1);

        log.info("Получен ответ от сервера статистики {}", endpointHit2);
    }
    public Long getHits(Long id){
        List<ViewStatsDto> stats;
        try {
            stats = client.getStats(
                    LocalDateTime.of(2020, 1, 1, 0, 0).format(formatter),
                    LocalDateTime.now().format(formatter), new String[]{"/events/" + id}, false);

        }catch (Exception e){
            return 0L;
        }

        if(stats == null || stats.isEmpty()){
            return 0L;
        }

        return stats.get(0).getHits();
    }
}
