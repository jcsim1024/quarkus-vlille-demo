package org.acme.vlille.services;

import io.quarkus.logging.Log;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.acme.vlille.dto.StationDTO;
import org.acme.vlille.entity.RawVlilleDataSet;
import org.acme.vlille.entity.Record;
import org.acme.vlille.exception.SynchronisationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.acme.vlille.dto.StationResponseDTO;
//import org.bson.codecs.pojo.


/**
 * @author Jean-Charles
 *
 *
 */
@ApplicationScoped
public class VLilleService {

	private static final List<StationDTO> cachedStations = new ArrayList<>();

	@Inject
	ReactiveMongoClient client;



	ReactiveMongoCollection<RawVlilleDataSet> collection;

	static OffsetDateTime lastimeCached;
	private static final int CACHE_EVICTION= 10;;






	/**
	 * Find all stations status.
	 *
	 * @return a list of stations.
	 * @throws SynchronisationException if we can't synchronise the station list
	 *                                  with remote API.
	 */
	public StationResponseDTO findAll() throws SynchronisationException {

		if (isUpdateCache()) {
			cachedStations.clear();
			Log.info("Main clear cache");
			final Uni<List<StationDTO>> loadedStations = this.performSynchronisation();

			var stations = loadedStations.onItem().transform(stationDTOS -> {
						AtomicInteger index = new AtomicInteger();
						stationDTOS.stream()
								.sorted(Comparator.comparing(StationDTO::getNom))
								.forEach(stationDTO -> stationDTO.setIndex(index.incrementAndGet()));
						return stationDTOS;
					})
					.onItem().invoke((stationDTOS) -> {cachedStations.addAll(stationDTOS);
						try {

							Thread.sleep(2000L);
							Log.info("print thread sleep");
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}).runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
					.await()
					.indefinitely();
			Log.info("Main thread");
			return new StationResponseDTO(stations, null);


		}
		return new StationResponseDTO(cachedStations, null);


	}

	/**
	 * Read data with vLille API
	 *
	 * @return a list of stations.
	 * @throws SynchronisationException if we can't synchronise the station list
	 *                                  with remote API.
	 */
	private Uni<List<StationDTO>> performSynchronisation() throws SynchronisationException {
		Multi<RawVlilleDataSet> ar = client.getDatabase("vlilledb").getCollection("vlillecol").withDocumentClass(RawVlilleDataSet.class).find();
		 return ar.onItem().transform(RawVlilleDataSet::getRecords)
				 .onItem().transform(VLilleService::toSationDTO).collect().asList();

//		var a = ar.onItem().invoke((found) -> Log.info(found)).collect().asList().await().indefinitely().;
//		ar.onCompletion();
//		  collection.aggregate(Arrays.asList(
//								new JsonObject("""
//										{$sort:
//										  {'records.fields.nom':-1}
//									 	}"""),
//								new JsonObject("""
//										{$group:
//											{_id:'$records.fields.nom',
//
//										    first:{$max:'$records.record_timestamp'}
//										    }
//
//										}
//										"""))).collect().asList();

		/*MongoCollection<Document> collection = mongoClient.getDatabase("vlilledb")
				.getCollection("vlillecol").withDocumentClass(Document.class);
*/

//		collection.
//				.aggregate(
//						Arrays.asList(
//								new JsonObject("""
//										{$sort:
//										  {'records.fields.nom':-1}
//									 	}"""),
//								new JsonObject("""
//										{$group:
//											{_id:'$records.fields.nom',
//
//										    first:{$max:'$records.record_timestamp'}
//										    }
//
//										}
//										""")
//						)
//				).;

//		return null;
//				vLilleDataSetRepo
//				.getRecords()
//				.stream()
//				.map(VLilleService::toSationDTO)
//				.toList();
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
