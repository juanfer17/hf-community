package com.hfcommunity.hf_community_hub.player;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRole;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRoleRepository;
import com.hfcommunity.hf_community_hub.role.Role;
import com.hfcommunity.hf_community_hub.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repo;
    private final PlayerModalityRoleRepository playerModalityRoleRepository;
    private final RoleRepository roleRepository;
    private final ModalityRepository modalityRepository;
    private final PasswordEncoder encoder;

    @Override
    public List<PlayerDTO> getAllPlayers() {
        return repo.findAll().stream()
                .map(p -> new PlayerDTO(p.getId(), p.getName(), p.getNickHabbo(), Collections.emptyList()))
                .toList();
    }

    @Override
    public PlayerDTO createPlayer(PlayerCreateDTO dto, boolean registered) {
        String salt = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        String hash = encoder.encode(dto.getPassword() + salt);

        Player player = new Player();
        player.setName(dto.getName());
        player.setEmail(dto.getEmail());
        player.setNickHabbo(dto.getNickhabbo());
        player.setPassword(hash);
        player.setSalt(salt);
        player.setIsActive(true);
        player.setIsRegistered(registered);

        return toDTO(repo.save(player));
    }

    @Override
    public PlayerDTO updateNickhabbo(Long id, String newNick) {
        Player player = repo.findById(id).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        player.setNickHabbo(newNick);
        return toDTO(repo.save(player));
    }

    @Override
    public void deletePlayer(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<PlayerDTO> getAllWithRoles() {
        return repo.findAll().stream()
                .map(this::toDTOWithSpecialRoles)
                .toList();
    }

    @Override
    public PlayerDTO updateRole(Long playerId, Long roleId, Long modalityId, String requesterRole) {
        if (!"SUPERADMIN".equalsIgnoreCase(requesterRole)) {
            throw new RuntimeException("No autorizado para asignar roles especiales");
        }

        Player player = repo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        boolean isSuperadmin = playerModalityRoleRepository.findByPlayerId(playerId).stream()
                .anyMatch(r -> r.getRole().getName().equalsIgnoreCase("SUPERADMIN"));

        if (isSuperadmin) {
            throw new RuntimeException("No se puede modificar a un SUPERADMIN");
        }

        boolean alreadyAssigned = playerModalityRoleRepository.findByPlayerId(playerId).stream()
                .anyMatch(r -> r.getRole().getId().equals(roleId) && r.getModality().getId().equals(modalityId));

        if (alreadyAssigned) {
            throw new RuntimeException("El jugador ya tiene ese rol asignado para esa modalidad");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));

        PlayerModalityRole newRole = new PlayerModalityRole();
        newRole.setPlayer(player);
        newRole.setRole(role);
        newRole.setModality(modality);
        playerModalityRoleRepository.save(newRole);

        return toDTOWithSpecialRoles(player);
    }

    private PlayerDTO toDTO(Player p) {
        List<Map<String, Object>> roles = playerModalityRoleRepository.findByPlayerId(p.getId())
                .stream()
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("modalityId", r.getModality().getId());
                    map.put("role", r.getRole().getName());
                    return map;
                })
                .collect(Collectors.toList());

        PlayerDTO dto = new PlayerDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setNickhabbo(p.getNickHabbo());
        dto.setRoles(roles);
        return dto;
    }


    private PlayerDTO toDTOWithSpecialRoles(Player player) {
        List<Map<String, Object>> roles = playerModalityRoleRepository.findByPlayerId(player.getId()).stream()
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("modalityId", r.getModality().getId());
                    map.put("role", r.getRole().getName());
                    return map;
                })
                .collect(Collectors.toList());

        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setNickhabbo(player.getNickHabbo());
        dto.setRoles(roles);
        return dto;
    }
}
