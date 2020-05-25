package org.acme.hello;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
@Produces("application/json")
@Consumes("application/json")
public class HelloResource {
  private static final Logger log = LoggerFactory.getLogger(HelloResource.class);

  @GET
  public HelloResponse hello() {
    log.info("GET /hello example log");
    return new HelloResponse("hello");
  }

  @POST
  public Response pushHello(HelloRequest param) {
    return Response.status(Response.Status.CREATED)
        .entity(new HelloResponse((param.getMessage())))
        .build();
  }
}
