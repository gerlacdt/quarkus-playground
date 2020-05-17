package org.acme.restclient;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Client for the public country API https://restcountries.eu
 */

@Path("/v2")
@RegisterRestClient(baseUri = "https://restcountries.eu/rest")
@RegisterProvider(value = NotFoundRestClientExceptionMapper.class)
public interface CountryGateway {

    @GET
    @Path("/name/{name}")
    @Produces("application/json")
    List<Country> getByName(@PathParam("name") String name);
}
