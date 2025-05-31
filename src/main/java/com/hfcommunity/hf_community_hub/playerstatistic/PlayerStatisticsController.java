package com.hfcommunity.hf_community_hub.playerstatistic;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/statistics")
@RequiredArgsConstructor
public class PlayerStatisticsController {

    private final PlayerStatisticsService service;

    @GetMapping("/top-scorers")
    public ResponseEntity<List<PlayerStatisticsDTO>> getTopScorers(
            @PathVariable("modality") String modality,
            @RequestParam("tournamentId") Long tournamentId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<PlayerStatisticsDTO> scorers = service.getTopScorersByTournamentAndModality(tournamentId, modalityId);
        return ResponseEntity.ok(scorers);
    }

    @GetMapping("/top-assistants")
    public ResponseEntity<List<PlayerStatisticsDTO>> getTopAssistProviders(
            @PathVariable("modality") String modality,
            @RequestParam("tournamentId") Long tournamentId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<PlayerStatisticsDTO> assistants = service.getTopAssistProviders(tournamentId, modalityId);
        return ResponseEntity.ok(assistants);
    }

    @GetMapping("/top-own-goals")
    public ResponseEntity<List<PlayerStatisticsDTO>> getTopOwnGoalScorers(
            @PathVariable("modality") String modality,
            @RequestParam("tournamentId") Long tournamentId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<PlayerStatisticsDTO> ownGoals = service.getTopOwnGoalScorers(tournamentId, modalityId);
        return ResponseEntity.ok(ownGoals);
    }

    @GetMapping("/top-mvps")
    public ResponseEntity<List<PlayerStatisticsDTO>> getTopMvps(
            @PathVariable("modality") String modality,
            @RequestParam("tournamentId") Long tournamentId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<PlayerStatisticsDTO> mvps = service.getTopMvps(tournamentId, modalityId);
        return ResponseEntity.ok(mvps);
    }

    @GetMapping("/top-mentions")
    public ResponseEntity<List<PlayerStatisticsDTO>> getTopMentions(
            @PathVariable("modality") String modality,
            @RequestParam("tournamentId") Long tournamentId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<PlayerStatisticsDTO> mentions = service.getTopMentions(tournamentId, modalityId);
        return ResponseEntity.ok(mentions);
    }
}
