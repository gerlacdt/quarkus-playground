package org.acme.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.acme.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  Logger log = LoggerFactory.getLogger(NotFoundException.class);

  @Override
  public Response toResponse(NotFoundException e) {
    var error = new ErrorResponse();
    error.setCode("NOT_FOUND");
    error.setMessage("not found");
    if (e != null) {
      error.setMessage(e.getMessage());
    }
    log.error("Not found error", e);

    // cannot add json body since Resteasy also has a default NotFoundExceptionMapper
    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
