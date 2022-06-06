package org.acme.vlille.services;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.acme.vlille.dto.StationDTO;
import org.acme.vlille.dto.StationResponseDTO;
import org.acme.vlille.entity.RawVlilleDataSet;
import org.acme.vlille.entity.Record;
import org.acme.vlille.entity.Station;

import javax.enterprise.context.ApplicationScoped;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.acme.vlille.entity.RawVlilleDataSet.aggregateByStationNameOrderByTime;


/**
 * @author Jean-Charles
 */
@ApplicationScoped
@Slf4j
public class VLilleService {

    private static final int CACHE_EVICTION_MIN = 10;
    private static final List<StationDTO> cachedStations = new ArrayList<>();
    private static OffsetDateTime lastimeCached = OffsetDateTime.now();

    public StationResponseDTO findAll() {

        switch (Pattern.match(cachedStations,lastimeCached)){
            case CACHED : return new StationResponseDTO(cachedStations, null);
            case OUTDATED_CACHE: cachedStations.clear();
            case NO_CACHE:
            default: return new StationResponseDTO( retrieveAndCacheStation(),null);
        }
    }

    private List<StationDTO> retrieveAndCacheStation() {
        final Uni<List<StationDTO>> loadedStations = this.getStationMapByName();
        log.info("---- #1 ----");
        var stationsUni = loadedStations
                .onItem().transform(stationDTOS -> {
                    AtomicInteger index = new AtomicInteger();
                    stationDTOS.stream()
                    .sorted(Comparator.comparing(StationDTO::getNom))
                    .forEach(stationDTO -> stationDTO.setIndex(index.incrementAndGet()));
            return stationDTOS;
        });

        stationsUni.emitOn(Infrastructure.getDefaultExecutor())
                .invoke((stationDTOS) -> {
                    ioSimulation(stationDTOS);
                    log.info("---- #3 ----");
                }).subscribe()
                .with(stationDTOS -> log.info("---- #4 ----"));

        log.info("---- #2.1 ----");
        var stations = stationsUni
                .await()
                .indefinitely();
        log.info("---- #2.2 ----");

        return stations;
    }

    @SneakyThrows
    private void ioSimulation(List<StationDTO> stationDTOS) {
        cachedStations.addAll(stationDTOS);
        lastimeCached = OffsetDateTime.now();
        Thread.sleep(8000L);
    }

    private Uni<List<StationDTO>> getStationMapByName() {
        return aggregateByStationNameOrderByTime()
                .onItem().transform(RawVlilleDataSet::getRecords)
                .onItem().transform(Record::getFields)
                .onItem().transform(VLilleService::toSationDTO)
                .collect().asList();

    }

    // TODO replace with mapstruct
    private static StationDTO toSationDTO(Station info) {
        final StationDTO station = new StationDTO();
        station.setNom(info.getNom());
        station.setNbvelosdispo(info.getNbvelosdispo());
        return station;
    }

    private enum Pattern {
        CACHED,NO_CACHE, OUTDATED_CACHE;

        public static Pattern match(List<StationDTO> cachedStations,OffsetDateTime lastimeCached){
                if (cachedStations.size() ==0)
                    return NO_CACHE;
                if (OffsetDateTime.now().minusMinutes(CACHE_EVICTION_MIN).isAfter(lastimeCached))
                    return OUTDATED_CACHE;
            return CACHED;
        }
    }
}
