package com.hfcommunity.hf_community_hub.playerteam;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import com.hfcommunity.hf_community_hub.team.TeamCreateDTO;
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
            @PathVariable String modality,
            @RequestBody PlayerTeamDTO playerTeamDTO
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        playerTeamService.registerPlayerToTeam(playerTeamDTO, modalityId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerTeamDTO>> getByTeam(
            @PathVariable String modality,
            @PathVariable Long teamId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(playerTeamService.getPlayersByTeam(teamId, modalityId));
    }


    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<PlayerTeamDTO>> getByPlayer(
            @PathVariable String modality,
            @PathVariable Long playerId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(playerTeamService.getTeamsByPlayer(playerId, modalityId));
    }
}
