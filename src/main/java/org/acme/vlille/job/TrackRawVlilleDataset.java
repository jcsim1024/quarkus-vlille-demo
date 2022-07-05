package org.acme.vlille.job;

import java.time.OffsetDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import org.acme.vlille.vlille.api.RawVlilleServiceRestEasy;
import org.acme.vlille.vlille.entity.RawVlilleDataSetEntity;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.scheduler.Scheduled;


@ApplicationScoped
@Slf4j
public class TrackRawVlilleDataset {

    @Inject
    @RestClient
    RawVlilleServiceRestEasy rawVlilleServiceRestEasy;

    @Scheduled(every = "10m")
    void pullDataset() {
        var dataset = rawVlilleServiceRestEasy.getDataSet();
        OffsetDateTime now = OffsetDateTime.now();


        dataset.getRecords().forEach(recordDocument -> {
             buildVlilleDataSetEntity(now, recordDocument.toString())
                     .persist().subscribe().with(item -> {log.info(String.valueOf(item));},
                             failure -> {log.error(String.valueOf(failure)) ;} );
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
