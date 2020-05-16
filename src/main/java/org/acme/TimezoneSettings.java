package org.acme;

import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import java.util.TimeZone;

@Singleton
public class TimezoneSettings {

    Logger log = LoggerFactory.getLogger(TimezoneSettings.class);

    public void setTimeZone(@Observes StartupEvent event) {
        log.info("set timezone to UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
