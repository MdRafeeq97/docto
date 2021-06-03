package com.app.docto.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DiskSpaceService implements HealthIndicator {
    @Override
    public Health health() {
        if(isDiskSpaceAvailable()) {
            return Health.up().withDetail("Disk Service", "Up").build();
        }
        return Health.down().withDetail("Disk Service", "Down").build();
    }

    private boolean isDiskSpaceAvailable() {
        return true;
    }
}
