package org.acme.vlille.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@ApplicationScoped
@RegisterRestClient
@JsonDeserialize(as = RawVlilleDataSet.class)
public interface RawVlilleServiceRestEasy {

	@GET
	@Produces("application/json")
	RawVlilleDataSet getDataSet();

}
