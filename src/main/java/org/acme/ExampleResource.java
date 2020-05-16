package org.acme;

import javax.ws.rs.*;

@Path("/hello")
@Produces("application/json")
@Consumes("application/json")
public class ExampleResource {

    @GET
    public HelloResponse hello() {
        return new HelloResponse("hello");
    }

    @POST
    public HelloResponse pushHello(HelloRequest param) {
        return new HelloResponse(param.getMessage());
    }
}