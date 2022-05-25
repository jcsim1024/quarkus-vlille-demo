package org.acme.vlille.job;

import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import org.acme.vlille.domain.RawVlilleServiceRestEasy;
import org.acme.vlille.entity.VlilleDataSetEntity;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@ApplicationScoped
public class TrackVlilleDataset {


    @Inject
    @RestClient
    RawVlilleServiceRestEasy rawVlilleServiceRestEasy;


    @Scheduled(every = "10m")
    void pullDataset() {
        var dataset = rawVlilleServiceRestEasy.getDataSet();

        Log.info(dataset);

        OffsetDateTime now = OffsetDateTime.now();
        dataset.getRecords()
                .forEach(jsonValue -> {
                            var vv = new VlilleDataSetEntity();
                            vv.setRecords(jsonValue.toString());
                            vv.setRetrivalTime(now);
                            vv.persist();
                        }
                );
    }


}
