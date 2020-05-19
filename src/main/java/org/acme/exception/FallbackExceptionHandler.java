package org.acme.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.acme.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class FallbackExceptionHandler implements ExceptionMapper<Exception> {

  Logger log = LoggerFactory.getLogger(FallbackExceptionHandler.class);

  @Override
  public Response toResponse(Exception e) {
    var error = new ErrorResponse();
    error.setCode("INTERNAL_SERVER_ERROR");
    error.setMessage(e.getMessage());

    log.error("Internal Server error", e);
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
  }
}
