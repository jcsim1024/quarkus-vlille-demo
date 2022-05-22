package org.acme.vlille.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.acme.vlille.domain.Record;
import org.acme.vlille.domain.VlilleServiceRestEasy;
import org.acme.vlille.dto.StationDTO;
import org.acme.vlille.dto.StationResponseDTO;
import org.acme.vlille.exception.SynchronisationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Jean-Charles
 *
 *
 */
@Service
public class VLilleService {

	private static final TreeMap<String, StationDTO> cachedStations = new TreeMap<>();

	@Inject
	@RestClient
	VlilleServiceRestEasy vService;

	/**
	 * Find all stations status.
	 *
	 * @return a list of stations.
	 * @throws SynchronisationException if we can't synchronise the station list
	 *                                  with remote API.
	 */
	public StationResponseDTO findAll() throws SynchronisationException {
		final StationResponseDTO stationResponseDTO = new StationResponseDTO();

		if (cachedStations.size() == 0) {
			final List<StationDTO> loadedStations = this.performSynchronisation();
			AtomicInteger index = new AtomicInteger();
			loadedStations.stream().sorted(Comparator.comparing(StationDTO::getNom ))
					.forEach(stationDTO -> {
						stationDTO.setIndex(index.incrementAndGet());
						cachedStations.put(stationDTO.getNom(), stationDTO);
					});

		}
		stationResponseDTO.setStations(new ArrayList<>(cachedStations.values()));


		return stationResponseDTO;
	}

	/**
	 * Read data with vLille API
	 *
	 * @return a list of stations.
	 * @throws SynchronisationException if we can't synchronise the station list
	 *                                  with remote API.
	 */
	private List<StationDTO> performSynchronisation() throws SynchronisationException {
		return vService
				.getDataSet()
				.getRecords()
				.stream()
				.map(VLilleService::toSationDTO)
				.toList();
	}



	private static StationDTO toSationDTO(Record info) {
		final StationDTO station = new StationDTO();
		station.setNom(info.getFields().getNom());
		station.setNbvelosdispo(info.getFields().getNbvelosdispo());
		return station;
	}

}
