package org.acme.hello;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
@Produces("application/json")
@Consumes("application/json")
public class HelloResource {
  private static final Logger log = LoggerFactory.getLogger(HelloResource.class);

  @GET
  @Counted(name = "get_hello", description = "count GET /hello requests")
  @Timed(name = "get_hello_duration", description = "http request duration")
  public HelloResponse hello() {
    log.info("GET /hello example log");
    return new HelloResponse("hello");
  }

  @POST
  @Counted(name = "post_hello", description = "count POST /hello requests")
  @Timed(name = "post_hello_duration", description = "http request duration")
  public Response pushHello(HelloRequest param) {
    return Response.status(Response.Status.CREATED)
        .entity(new HelloResponse((param.getMessage())))
        .build();
  }
}