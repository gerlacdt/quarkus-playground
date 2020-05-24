package org.acme.middleware;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class MetricsFilter implements ContainerRequestFilter {

  Logger log = LoggerFactory.getLogger(MetricsFilter.class);

  public void filter(ContainerRequestContext ctx) {
    var uriInfo = ctx.getUriInfo();
    var matchedUris = uriInfo.getMatchedURIs();
    log.info("uris={}", matchedUris);
    var pathParams = uriInfo.getPathParameters(true);
    log.info("pathParams={}", pathParams);
    var uri = matchedUris.get(0);
    log.info("handler: {}", toTemplateUri(uri, pathParams));
  }

  public static String toTemplateUri(String matchedUri, MultivaluedMap<String, String> pathParams) {
    var uri = matchedUri;
    for (String key : pathParams.keySet()) {
      var val = pathParams.getFirst(key);
      uri = uri.replaceAll(val, String.format("{%s}", key));
    }
    return uri;
  }
}
