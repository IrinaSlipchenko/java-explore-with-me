package ru.practicum.ewm.statistics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.ewm.statistics.dto.EndpointHitDto;
import ru.practicum.ewm.statistics.dto.ViewStatsDto;

import java.util.List;

@FeignClient(value = "stats", url = "${feign.url}")
public interface StatsClient {

    @GetMapping("/stats?start={start}&end={end}&uris={uris}&unique={unique}")
    List<ViewStatsDto> getStats(@PathVariable String start, @PathVariable String end,
                                @PathVariable String[] uris, @PathVariable boolean unique);

    @PostMapping("/hit")
    EndpointHitDto setStat(@RequestBody EndpointHitDto endpointHit);
}


