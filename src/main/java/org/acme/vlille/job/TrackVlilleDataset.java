package org.acme.vlille.job;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import org.acme.vlille.domain.VlilleServiceRestEasy;
import org.acme.vlille.entity.VlilleDataSetEntity;
import org.acme.vlille.services.VLilleService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.stereotype.Service;


@ApplicationScoped
public class TrackVlilleDataset {


    @Inject
    @RestClient
    VlilleServiceRestEasy vLilleService;

    @Inject
    VlilleDataSetEntity vlilleDataSetEntity;

    @Scheduled(every = "1m")
    void pullDataset() {
        var records = vLilleService.getDataSet().getRecords();
        Log.info(records);
        var entity = new VlilleDataSetEntity();
        entity.setRecords(records);
        entity.setRetrivalTime(OffsetDateTime.now());
        entity.persist();
    }


}
