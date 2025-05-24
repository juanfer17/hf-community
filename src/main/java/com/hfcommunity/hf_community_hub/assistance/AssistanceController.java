package com.hfcommunity.hf_community_hub.assistance;

import com.hfcommunity.hf_community_hub.common.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assistance")
@RequiredArgsConstructor
public class AssistanceController {

    private final AssistanceService service;
    private final RateLimiterService rateLimiterService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody AssistanceRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getHeader("X-Forwarded-For");
        if (ip == null) ip = httpRequest.getRemoteAddr();

        if (!rateLimiterService.isAllowed(ip)) {
            return ResponseEntity.status(429).body(Map.of("message", "Demasiados intentos. Espera un momento."));
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El nombre es obligatorio."));
        }

        try {
            service.register(request.getName(), httpRequest);
            return ResponseEntity.status(201).body(Map.of("message", "Asistencia registrada correctamente."));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(429).body(Map.of("message", ex.getMessage()));
        }
    }


    @GetMapping
    public List<AssistanceDTO> getAll(Authentication auth) {
        return service.getAll(auth);
    }
}