package org.acme.restclient;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class NotFoundRestClientExceptionMapper implements ResponseExceptionMapper {

    private static final Logger log = LoggerFactory.getLogger(NotFoundRestClientExceptionMapper.class);

    @Override
    public Throwable toThrowable(Response response) {
        var body = response.readEntity(String.class);
        return new WebApplicationException(body);
    }

    @Override
    public int getPriority() {
        return 50;
    }

    @Override
    public boolean handles(int status, MultivaluedMap headers) {
        return status == 404;
    }
}
