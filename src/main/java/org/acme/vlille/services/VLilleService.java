package org.acme.vlille.services;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClientSettings;
import io.quarkus.logging.Log;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import liquibase.pro.packaged.D;
import lombok.extern.slf4j.Slf4j;
import org.acme.vlille.dto.StationDTO;
import org.acme.vlille.entity.RawResult;
import org.acme.vlille.entity.RawVlilleDataSet;
import org.acme.vlille.entity.Record;
import org.acme.vlille.exception.SynchronisationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.management.ManagementFactory;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.acme.vlille.dto.StationResponseDTO;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.json.JsonObject;
//import org.bson.codecs.pojo.


/**
 * @author Jean-Charles
 *
 *
 */
@ApplicationScoped
@Slf4j
public class VLilleService {

	public static final String JS_REQUEST = """
			[
				{$sort:
					{'records.fields.nom':-1,'records.record_timestamp':1}
				},
				{$group:
			  		{_id:'$records.fields.nom',
			 			first:{$first:'$$ROOT'}
				  	}
			    
			 	}
			 	]
			""";
	private static final List<StationDTO> cachedStations = new ArrayList<>();

	@Inject
	ReactiveMongoClient client;



	ReactiveMongoCollection<RawVlilleDataSet> collection;

	static OffsetDateTime lastimeCached = OffsetDateTime.now();
	private static final int CACHE_EVICTION= 10;;






	/**
	 * Find all stations status.
	 *
	 * @return a list of stations.
	 * @throws SynchronisationException if we can't synchronise the station list
	 *                                  with remote API.
	 */
	public StationResponseDTO findAll() throws SynchronisationException {

		if (isUpdateCache() || true) {
			cachedStations.clear();

			final Uni<List<StationDTO>> loadedStations = this.performSynchronisation();
			log.info("---- #1 ----");
			var stationsUni = loadedStations.onItem().transform(stationDTOS -> {
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

			return new StationResponseDTO(stations, null);
		}
		return new StationResponseDTO(cachedStations, null);
	}

	private void ioSimulation(List<StationDTO> stationDTOS) {
		cachedStations.addAll(stationDTOS);
		lastimeCached = OffsetDateTime.now();
		try {

			Thread.sleep(8000L);

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private Uni<List<StationDTO>> performSynchronisation() {



		var collection = client.getDatabase("vlilledb").getCollection("vlillecol");
		var specifications = getListDocument(JS_REQUEST);




		return collection
				.withDocumentClass(RawResult.class)
				.aggregate(specifications)
				.onItem().transform(RawResult::getFirst)
				.onItem().transform(RawVlilleDataSet::getRecords)
				.onItem().transform(VLilleService::toSationDTO).collect().asList();

	}

	private List<Document> getListDocument(String jsRequest) {
		return Document.parse("{\"json\":" + jsRequest + "}").getList("json", Document.class);
	}


	// TODO replace with mapstruct
	private static StationDTO toSationDTO(Record info) {
		final StationDTO station = new StationDTO();
		station.setNom(info.getFields().getNom());
		station.setNbvelosdispo(info.getFields().getNbvelosdispo());
		return station;
	}

	private boolean isUpdateCache() {
		return cachedStations.size() == 0
				|| OffsetDateTime.now().minusMinutes(CACHE_EVICTION).isAfter(lastimeCached);

	}
}
