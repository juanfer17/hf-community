package com.hfcommunity.hf_community_hub.match;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ARBITRO')")
    public ResponseEntity<MatchDTO> createMatch(
            @PathVariable("modality") String modality,
            @Valid @RequestBody MatchCreateDTO dto,
            Authentication auth
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.status(201).body(matchService.createMatch(modalityId, dto, auth));
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches(
            @PathVariable("modality") String modality
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(matchService.getAllMatchesByModality(modalityId));
    }
}