package org.acme.vlille.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.acme.vlille.domain.Record;
import org.acme.vlille.domain.VlilleDataSet;
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

	private static final HashMap<String, StationDTO> stations = new HashMap<>();

	Logger logger = LoggerFactory.getLogger(VLilleService.class);

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
		// Check if we have stations in the map...
		// Here we will later externalize the cache from the app in order to make the
		// hash map loading faster

		if (stations.size() == 0) {
			final List<StationDTO> loadedStations = this.performSynchronisation();
			// We add a station in map for each entry
			for (final StationDTO station : loadedStations) {
				stations.put(station.getNom(), station);
			}

			stationResponseDTO.setStations(loadedStations);
		} else {
			stationResponseDTO.setStations(new ArrayList<>(stations.values()));
		}

		return stationResponseDTO;
	}

	/**
	 * Read data with vLille API
	 *
	 * @return a list of stations.
	 * @throws SynchronisationException if we can't synchronise the station list
	 *                                  with remote API.
	 */
	public List<StationDTO> performSynchronisation() throws SynchronisationException {

		final VlilleDataSet dataSet = vService.getDataSet();

		List<StationDTO> listeStation;
		listeStation = metierVersContrat(dataSet);
		return listeStation;
	}

	private List<StationDTO> metierVersContrat(final VlilleDataSet dataSet) {
		final List<StationDTO> listeStation;
		listeStation = new ArrayList<>();

		final List<Record> lstinfo = dataSet.getRecords();
		for (final Record info : lstinfo) {
			final StationDTO station = new StationDTO();
			station.setNom(info.getFields().getNom());
			station.setNbvelosdispo(info.getFields().getNbvelosdispo());
			listeStation.add(station);
		}
		return listeStation;
	}

}
