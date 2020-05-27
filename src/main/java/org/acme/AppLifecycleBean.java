package org.acme;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import java.util.TimeZone;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AppLifecycleBean {
  private static final Logger log = LoggerFactory.getLogger(AppLifecycleBean.class);

  /**
   * Initializes Timezone to UTC.
   *
   * @param event quarkus startup event
   */
  public void onStart(@Observes StartupEvent event) {
    log.info(
        "Starting application, set timezone to UTC, quarkus.profile={}",
        ProfileManager.getActiveProfile());
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}
