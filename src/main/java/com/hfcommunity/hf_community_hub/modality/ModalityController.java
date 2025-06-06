package com.hfcommunity.hf_community_hub.modality;

import com.hfcommunity.hf_community_hub.tournament.TournamentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modalities")
@RequiredArgsConstructor
public class ModalityController {

    private final ModalityService service;

    @GetMapping
    public ResponseEntity<List<ModalityDTO>> getAll() {
        return ResponseEntity.ok(service.getAllModalities());
    }

    @GetMapping("/referee")
    @PreAuthorize("hasAnyRole('ARBITRO', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ModalityDTO>> getModalitiesWhereUserIsReferee(@RequestParam Long playerId) {
        return ResponseEntity.ok(service.getModalitiesByRefereeId(playerId));
    }

    @GetMapping("/{modalityId}/tournaments")
    @PreAuthorize("hasAnyRole('ARBITRO', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByModality(
            @PathVariable Long modalityId) {
        return ResponseEntity.ok(service.getTournamentsByModality(modalityId));
    }
}
