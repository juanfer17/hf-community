package com.hfcommunity.hf_community_hub.playerteam;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/player-teams")
@RequiredArgsConstructor
public class PlayerTeamController {

    private final PlayerTeamService playerTeamService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestParam Long playerId,
            @RequestParam Long teamId,
            @PathVariable String modality
    ) {
        playerTeamService.registerPlayerToTeam(playerId, teamId, modality.toUpperCase());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerTeamDTO>> getByTeam(
            @PathVariable String modality,
            @PathVariable Long teamId
    ) {
        return ResponseEntity.ok(playerTeamService.getPlayersByTeam(teamId, modality.toUpperCase()));
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<PlayerTeamDTO>> getByPlayer(
            @PathVariable String modality,
            @PathVariable Long playerId
    ) {
        return ResponseEntity.ok(playerTeamService.getTeamsByPlayer(playerId, modality.toUpperCase()));
    }
}
