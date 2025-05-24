package com.hfcommunity.hf_community_hub.tournament;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/{modality}/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> createTournament(
            @PathVariable("modality") String modality,
            @Valid @RequestBody TournamentCreateDTO dto) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.status(201).body(service.createTournament(modalityId, dto));
    }

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournaments(
            @PathVariable("modality") String modality) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(service.getAllTournamentsByModality(modalityId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(
            @PathVariable("modality") String modality,
            @PathVariable Long id) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(service.getTournamentByIdAndModality(id, modalityId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteTournament(
            @PathVariable("modality") String modality,
            @PathVariable Long id) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        service.deleteTournament(id, modalityId);
        return ResponseEntity.ok(Map.of("message", "Torneo eliminado exitosamente"));
    }
}