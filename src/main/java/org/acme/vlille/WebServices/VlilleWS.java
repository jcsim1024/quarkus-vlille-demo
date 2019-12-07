package org.acme.vlille.WebServices;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.vlille.dto.StationResponseDTO;
import org.acme.vlille.exception.SynchronisationException;
import org.acme.vlille.services.VLilleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/stations")
@Produces(MediaType.APPLICATION_JSON)
public class VlilleWS {
	Logger logger = LoggerFactory.getLogger(VlilleWS.class);

	/**
	 * VLille Service.
	 */
	@Inject
	private VLilleService vLilleService;

	/**
	 * Return a list of stations.
	 * 
	 * @return a list of stations.
	 */
	@Path("/findAll")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public StationResponseDTO findAll() throws SynchronisationException {
		final Long startTime = System.currentTimeMillis();
		final StationResponseDTO stationResponseDTO = vLilleService.findAll();
		stationResponseDTO.setTime(System.currentTimeMillis() - startTime);
		return stationResponseDTO;
	}
}