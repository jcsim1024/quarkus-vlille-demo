package org.acme.vlille.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.acme.vlille.dto.StationDTO;
import org.acme.vlille.dto.StationResponseDTO;
import org.acme.vlille.exception.SynchronisationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jean-Charles
 * 
 * 
 */
@Service
public class VLilleService {

	Logger logger = LoggerFactory.getLogger(VLilleService.class);

	@ConfigProperty(name = "vlillehttps", defaultValue = "")
	String vlilleWs;

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
		final HashMap<String, StationDTO> stations = new HashMap<String, StationDTO>();

		if (stations.size() == 0) {
			final List<StationDTO> loadedStations = this.performSynchronisation();
			// We add a station in map for each entry
			for (final StationDTO station : loadedStations) {
				stations.put(station.getNom(), station);
			}

			stationResponseDTO.setStations(loadedStations);
		} else {
			stationResponseDTO.setStations(new ArrayList<StationDTO>(stations.values()));
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

		final RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<String> responseStr = restTemplate.exchange(vlilleWs, HttpMethod.GET, null, String.class);

		JsonNode root = null;
		List<StationDTO> listeStation = new ArrayList<StationDTO>();
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {

			root = mapper.readTree(responseStr.getBody());
		} catch (IOException e) {
			logger.error(e.toString());
			throw new SynchronisationException(e.toString());

		}

		helperJsonNodeToDto(root, listeStation);

		return listeStation;
	}

	private void helperJsonNodeToDto(JsonNode root, List<StationDTO> listeStation) {
		JsonNode temp = root.get("records");
		for (int i = 0; i < temp.size(); i++) {
			JsonNode tmpJsSta = temp.get(i).get("fields");
			StationDTO tmpSta = new StationDTO();
			tmpSta.setNbvelosdispo(tmpJsSta.get("nbvelosdispo").toString());
			tmpSta.setNom(tmpJsSta.get("nom").toString());
			listeStation.add(tmpSta);
		}
	}

	/**
	 * display all members of node
	 * 
	 * @param root
	 * @param arrows
	 */
	private void recursiveNodeDisplay(JsonNode root, String arrows) {
		Iterator<String> iterator = root.fieldNames();
		while (iterator.hasNext() && !root.isArray()) {
			String fieldName = iterator.next();
			JsonNode temp = root.path(fieldName);
			logger.info(arrows + fieldName);
			if (temp.isContainerNode() && !temp.isArray()) {
				recursiveNodeDisplay(temp, arrows + "---");
			}
			if (fieldName.contentEquals("records")) {
				logger.info("size:"+new Integer(temp.size()).toString());
				for (int i = 0; i < temp.size(); i++) {
					recursiveNodeDisplay(temp.get(i), arrows + "---");
					logger.info("--");

				}
			}

		}

	}
}
