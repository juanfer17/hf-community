package com.hfcommunity.hf_community_hub.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class HealthCheckWorkerConfig {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckWorkerConfig.class);
    private static final String HEALTH_ENDPOINT = "http://localhost:8080/health";

    @Scheduled(fixedRate = 15000) // 15 seconds in milliseconds
    public void checkHealth() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(HEALTH_ENDPOINT, String.class);
            logger.info("Health check response: {}", response);
        } catch (Exception e) {
            logger.error("Error during health check: {}", e.getMessage());
        }
    }
}
