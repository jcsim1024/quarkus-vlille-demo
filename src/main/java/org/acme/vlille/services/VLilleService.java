package org.acme.vlille.services;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.vlille.dto.StationDTO;
import org.acme.vlille.dto.StationResponseDTO;
import org.acme.vlille.entity.Record;
import org.acme.vlille.entity.Station;
import org.acme.vlille.entity.VlilleDataSet;

import javax.enterprise.context.ApplicationScoped;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.acme.vlille.entity.VlilleDataSet.aggregateByStationNameOrderByTime;


/**
 * @author Jean-Charles
 */
@ApplicationScoped
@Slf4j
public class VLilleService {

    private static final int CACHE_EVICTION_MIN = 10;
    private static final Map<String,StationDTO> cachedStations = new TreeMap<>() ;
    private static OffsetDateTime lastimeCached = OffsetDateTime.now();

    public StationResponseDTO.StationResponseDTOBuilder findAll() {

        var respBuilder = StationResponseDTO.builder();
         var stations = switch (new GuardedPattern(cachedStations,lastimeCached) ){
            case GuardedPattern p && p.isCached() ->cachedStations ;
             case GuardedPattern p && p.isOutDated() -> {
                 cachedStations.clear();
                 yield retrieveAndCacheStation();
             }
             default ->  retrieveAndCacheStation();
        };

        return respBuilder.stations(stations.values().stream().toList());
    }

    private Map<String,StationDTO> retrieveAndCacheStation() {
        final Uni<Map<String,StationDTO>> loadedStations = this.getStationMapByName();
        log.info("---- #1 ----");
        var stationsUni = loadedStations
                .onItem().transform(stationDTOS -> {
                    AtomicInteger index = new AtomicInteger();
                    stationDTOS.values().stream()
                    .sorted(Comparator.comparing(StationDTO::getNom))
                    .forEach(stationDTO -> stationDTO.setIndex(index.incrementAndGet()));
            return stationDTOS;
        });

        stationsUni.emitOn(Infrastructure.getDefaultWorkerPool())
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


    private void ioSimulation(Map<String,StationDTO> stationDTOS) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cachedStations.putAll(stationDTOS);
        lastimeCached = OffsetDateTime.now();
    }

    private Uni<Map<String,StationDTO>> getStationMapByName() {
        return aggregateByStationNameOrderByTime()
                .onItem().transform(VlilleDataSet::getRecords)
                .onItem().transform(Record::getFields)
                .onItem().transform(VLilleService::toSationDTO)
                .collect()
                .asMap(StationDTO::getNom,stationDTO -> stationDTO);

    }

    // TODO replace with mapstruct
    private static StationDTO toSationDTO(Station info) {
        final StationDTO station = new StationDTO();
        station.setNom(info.getNom());
        station.setNbvelosdispo(info.getNbvelosdispo());
        return station;
    }


    @AllArgsConstructor
    private class GuardedPattern {
        Map<String,StationDTO> cachedStations;
        OffsetDateTime lastimeCached;

        boolean isCached(){
            return cachedStations.size()>0 && !isOutDated() ;
        }

        boolean isOutDated() {
            return OffsetDateTime.now().minusMinutes(CACHE_EVICTION_MIN).isAfter(lastimeCached);
        }

    }
}
