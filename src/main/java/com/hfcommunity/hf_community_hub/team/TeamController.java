package com.hfcommunity.hf_community_hub.team;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/{modality}/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(
            @PathVariable("modality") String modality,
            @RequestBody TeamCreateDTO teamDTO
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.createTeam(teamDTO, null, modalityId));
    }


    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(
            @PathVariable("modality") String modality,
            @RequestParam("tournamentId") Long tournamentId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(teamService.getAllTeams(modalityId, tournamentId));
    }

    @GetMapping("/with-logo")
    public ResponseEntity<List<TeamDTO>> getAllTeamsWithLogos(@PathVariable String modality) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(teamService.getAllTeamsWithLogo(modalityId));
    }

    @GetMapping("/tournament/{torneoId}")
    public ResponseEntity<List<TeamDTO>> getTeamsByTorneo(
            @PathVariable String modality,
            @PathVariable Long torneoId
    ) {

        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(teamService.getTeamsByTournament(torneoId, modalityId));
    }

    @GetMapping("/dt/{dtId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TeamDTO>> getTeamsByDt(
            @PathVariable("modality") String modality,
            @PathVariable Long dtId) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return ResponseEntity.ok(teamService.getTeamsByDt(modalityId, dtId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok(Map.of("message", "Equipo eliminado correctamente"));
    }
}