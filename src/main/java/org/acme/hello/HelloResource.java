package org.acme.hello;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
@Produces("application/json")
@Consumes("application/json")
public class HelloResource {
  private static final Logger log = LoggerFactory.getLogger(HelloResource.class);

  /**
   * Returns hello.
   *
   * @return hello
   */
  @GET
  public HelloResponse hello() {
    return new HelloResponse("hello");
  }

  /**
   * Says a hello with the given message.
   *
   * @param param some json body
   * @return a json response
   */
  @POST
  public Response pushHello(HelloRequest param) {
    var message = "";
    if (param != null && param.getMessage() != null) {
      message = param.getMessage();
    }
    return Response.status(Response.Status.CREATED).entity(new HelloResponse(message)).build();
  }
}
