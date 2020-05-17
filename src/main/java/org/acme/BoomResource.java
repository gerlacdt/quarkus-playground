package org.acme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/boom")
@Produces("application/json")
@Consumes("application/json")
public class BoomResource {

    private static final Logger log = LoggerFactory.getLogger(BoomResource.class);

    @GET
    public Response boom() {
        throw new RuntimeException("BOOM, request exploded");
    }
}
