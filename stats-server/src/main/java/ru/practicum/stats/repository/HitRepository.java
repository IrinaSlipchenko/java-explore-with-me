package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select app, uri, count (ip) as hits from EndpointHit " +
            "where uri in :uris and (created >= :start and created <= :end)")
    List<HitDto> getHits(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select app, uri, count (distinct ip) as hits from EndpointHit " +
            "where uri in :uris and (created >= :start and created <= :end)")
    List<HitDto> getUniqueHits(LocalDateTime start, LocalDateTime end, List<String> uris);

}
