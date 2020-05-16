package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/hello")
@Produces("application/json")
@Consumes("application/json")
public class ExampleResource {

    @GET
    public HelloResponse hello() {
        return new HelloResponse("hello");
    }

    @POST
    public Response pushHello(HelloRequest param) {
        return Response.status(Response.Status.CREATED).entity(new HelloResponse((param.getMessage()))).build();
    }
}