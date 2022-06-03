package org.acme.vlille.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.acme.vlille.entity.VlilleDataSet;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Path("/")
@ApplicationScoped
@RegisterRestClient
@JsonDeserialize(as = VlilleDataSet.class)
public interface VlilleServiceRestEasy {

	@GET
	@Produces("application/json")
	VlilleDataSet getDataSet();

}
