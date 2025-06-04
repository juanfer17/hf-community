package com.hfcommunity.hf_community_hub.player;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAll() {
        return ResponseEntity.ok(service.getAllPlayers());
    }

    @PostMapping("/unregistered")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerDTO> createUnregistered(@RequestBody PlayerCreateDTO dto) {
        return ResponseEntity.status(201).body(service.createPlayer(dto, false));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerDTO> updateNick(@PathVariable Long id, @RequestParam String nick) {
        return ResponseEntity.ok(service.updateNickhabbo(id, nick));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<PlayerDTO>> getRoles() {
        return ResponseEntity.ok(service.getAllWithRoles());
    }

    @PutMapping("/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<PlayerDTO> updateRole(@RequestParam Long id,
                                                @PathVariable("modality") String modality,
                                                @RequestParam Long roleId,
                                                Authentication auth) {

        Long modalityId = ModalityEnum.fromName(modality).getId();
        String requesterRole = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "").toUpperCase();
        return ResponseEntity.ok(service.updateRole(id, roleId, modalityId, requesterRole));
    }
}
