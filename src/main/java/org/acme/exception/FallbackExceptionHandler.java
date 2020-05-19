package org.acme.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.acme.ErrorResponse;

@Provider
public class FallbackExceptionHandler implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception e) {
    var error = new ErrorResponse();
    error.setCode("INTERNAL_SERVER_ERROR");
    error.setMessage(e.getMessage());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
  }
}
