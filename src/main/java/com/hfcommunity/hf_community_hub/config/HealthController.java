package com.hfcommunity.hf_community_hub.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/")
    public String health() {
        return "OK";
    }
}
