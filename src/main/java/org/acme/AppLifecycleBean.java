package org.acme;

import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.TimeZone;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger log = LoggerFactory.getLogger(AppLifecycleBean.class);

    public void onStart(@Observes StartupEvent event) {
        log.info("set timezone to UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
