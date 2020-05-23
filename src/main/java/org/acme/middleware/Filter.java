package org.acme.middleware;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class Filter implements ContainerRequestFilter {

  Logger log = LoggerFactory.getLogger(Filter.class);

  public void filter(ContainerRequestContext ctx) {
    var uriInfo = ctx.getUriInfo();
    var matchedUris = uriInfo.getMatchedURIs();
    log.info("uris={}", matchedUris);
  }
}
