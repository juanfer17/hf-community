package com.hfcommunity.hf_community_hub.assistance;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssistanceService {

    private final AssistanceRepository repository;

    public void register(String name, HttpServletRequest request) {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        repository.findFirstByNameAndDateTimeAfter(name, oneMinuteAgo)
                .ifPresent(a -> {
                    throw new RuntimeException("Ya registraste asistencia recientemente");
                });

        Assistance asistencia = new Assistance();
        asistencia.setName(name);
        asistencia.setDateTime(LocalDateTime.now());
        asistencia.setIp(extractClientIp(request));

        repository.save(asistencia);
    }

    public List<AssistanceDTO> getAll(Authentication authentication) {
        boolean isAdmin = authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")
                                || auth.getAuthority().equals("ROLE_SUPERADMIN"));

        return repository.findAll().stream()
                .map(a -> new AssistanceDTO(
                        a.getName(),
                        a.getDateTime(),
                        isAdmin ? a.getIp() : null
                ))
                .collect(Collectors.toList());
    }

    private String extractClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        return (forwarded != null && !forwarded.isBlank())
                ? forwarded.split(",")[0].trim()
                : request.getRemoteAddr();
    }
}
