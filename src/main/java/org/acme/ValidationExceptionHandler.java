package org.acme;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException e) {
    var error = new ErrorResponse();
    error.setCode("INVALID_REQUEST_BODY");
    error.setMessage(e.getMessage());
    return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
  }
}
