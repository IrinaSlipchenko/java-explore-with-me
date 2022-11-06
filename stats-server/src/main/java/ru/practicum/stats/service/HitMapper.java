package ru.practicum.stats.service;

import org.mapstruct.Mapper;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HitMapper {

    EndpointHit toHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> toStats(List<HitDto> hitsDto);

}
