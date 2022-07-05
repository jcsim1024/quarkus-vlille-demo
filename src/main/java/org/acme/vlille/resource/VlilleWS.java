package org.acme.vlille.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;
import org.acme.vlille.dto.StationResponseDTO;
import org.acme.vlille.services.VLilleService;

@Path("/api/stations")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class VlilleWS {


	@Inject
	VLilleService vLilleService;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public StationResponseDTO findAll() {
		var startTime = System.currentTimeMillis();
		var stationResponseDTO = vLilleService.findAll();
		var duration =System.currentTimeMillis() - startTime;
		log.info("Time spent : {}ms",duration);
		return stationResponseDTO
				.time(duration)
				.build();
	}
}