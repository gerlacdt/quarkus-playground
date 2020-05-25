package org.acme.user;

import io.quarkus.arc.AlternativePriority;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class FakeProducer {

  Logger log = LoggerFactory.getLogger(FakeProducer.class);

  @ConfigProperty(name = "fakeEnabled", defaultValue = "true")
  private boolean fakeEnabled;

  @Produces
  @ApplicationScoped
  @AlternativePriority(1)
  public UserRepository getUserRepository(SqlUserRepository sqlUserRepository) {
    if (fakeEnabled) {
      log.info(
          "in producer method, fakeEnabled: {}, repo: {}",
          fakeEnabled,
          FakeUserRepository.class.toString());
      return new FakeUserRepository();
    }
    log.info(
        "in producer method, fakeEnabled: {}, repo: {}",
        fakeEnabled,
        sqlUserRepository.getClass().toString());
    return sqlUserRepository;
  }
}
