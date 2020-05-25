package org.acme.middleware;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class MetricsRequestFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext reqCtx) throws IOException {
    reqCtx.setProperty("startTime", System.currentTimeMillis());
  }
}
