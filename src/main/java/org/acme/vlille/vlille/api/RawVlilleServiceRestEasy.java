package org.acme.vlille.vlille.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Path("/")
@ApplicationScoped
@RegisterRestClient
@JsonDeserialize(as = RawVlilleDataSetDTO.class)
public interface RawVlilleServiceRestEasy {

	@GET
	@Produces("application/json")
	RawVlilleDataSetDTO getDataSet();

}
