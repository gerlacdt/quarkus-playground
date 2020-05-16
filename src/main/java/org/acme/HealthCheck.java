package org.acme;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class HealthCheck implements org.eclipse.microprofile.health.HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("simple health check");
    }
}
