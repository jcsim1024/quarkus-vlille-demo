package org.acme.vlille.job;

import java.time.OffsetDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.vlille.vlille.api.RawVlilleServiceRestEasy;
import org.acme.vlille.vlille.entity.RawVlilleDataSetEntity;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;


@ApplicationScoped
@Slf4j
public class TrackRawVlilleDataset {

    @Inject
    @RestClient
    RawVlilleServiceRestEasy rawVlilleServiceRestEasy;

    @Scheduled(every = "10m" , delay = 1L)
    void pullDataset() {
        var dataset = rawVlilleServiceRestEasy.getDataSet();
        OffsetDateTime now = OffsetDateTime.now();

        log.debug(dataset);

        dataset.getRecords().forEach(recordDocument -> {
             buildVlilleDataSetEntity(now, recordDocument.toString())
                     .persist().subscribe();
        });
    }

    private RawVlilleDataSetEntity buildVlilleDataSetEntity(OffsetDateTime now, String record) {
        var entityBSON = new RawVlilleDataSetEntity();
        // not to be confused with jackson library
        entityBSON.setRecords(new org.bson.json.JsonObject(record));
        entityBSON.setRetrivalTime(now);
        return entityBSON;
    }


}
