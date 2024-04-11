package com.rewards.backend.TEMP_PACKAGE;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ExternalServiceHealthIndicator implements HealthIndicator {

    private boolean externalServiceIsHealthy = true; // Set this based on your actual health check logic

    @Override
    public Health health() {
        if (externalServiceIsHealthy) {
            return Health.up().build();
        } else {
            return Health.down().withDetail("reason", "External service is not responding").build();
        }
    }
}
