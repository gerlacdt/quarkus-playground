package org.acme.middleware;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class MetricsResponseFilter implements ContainerResponseFilter {

  Logger log = LoggerFactory.getLogger(MetricsResponseFilter.class);

  @Inject MetricRegistry metricRegistry;

  @Override
  public void filter(ContainerRequestContext reqCtx, ContainerResponseContext resCtx)
      throws IOException {
    var uriInfo = reqCtx.getUriInfo();
    var matchedUris = uriInfo.getMatchedURIs();
    var pathParams = uriInfo.getPathParameters(true);
    String uri = "NOT_FOUND";
    if (matchedUris.size() > 0) {
      uri = matchedUris.get(0);
    }
    var method = reqCtx.getMethod();
    var statusCode = resCtx.getStatus();
    var handler = toTemplateUri(uri, pathParams);
    metricRegistry
        .counter(
            Metadata.builder()
                .withName("http_requests")
                .withDescription("number of http requests")
                .build(),
            new Tag("method", method),
            new Tag("handler", handler),
            new Tag("code", String.valueOf(statusCode)))
        .inc();

    long duration = System.currentTimeMillis() - (long) reqCtx.getProperty("startTime");
    metricRegistry
        .histogram(
            Metadata.builder()
                .withName("http_request_duration")
                .withUnit(MetricUnits.MILLISECONDS)
                .withDescription("duration of a request")
                .build(),
            new Tag("method", method),
            new Tag("handler", handler),
            new Tag("code", String.valueOf(statusCode)))
        .update(duration);
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
