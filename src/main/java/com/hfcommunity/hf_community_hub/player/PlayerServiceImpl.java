package com.hfcommunity.hf_community_hub.player;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public List<PlayerDTO> getAllPlayers() {
        return repo.findAll().stream()
                .map(p -> new PlayerDTO(p.getId(), p.getName(), p.getNickHabbo(), p.getRole()))
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
        player.setRole("jugador");
        player.setIsActive(true);
        player.setIsRegistered(registered);

        return toDTO(repo.save(player));
    }

    @Override
    public PlayerDTO updateNickhabbo(Long id, String newNick) {
        Player player = repo.findById(id).orElseThrow();
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
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PlayerDTO updateRole(Long id, String newRole, String requesterRole) {
        if (!requesterRole.equals("superadmin") && !List.of("jugador", "dt", "arbitro").contains(newRole))
            throw new RuntimeException("Unauthorized");

        Player player = repo.findById(id).orElseThrow();
        if ("superadmin".equals(player.getRole()))
            throw new RuntimeException("Cannot change superadmin");

        player.setRole(newRole);
        return toDTO(repo.save(player));
    }

    private PlayerDTO toDTO(Player p) {
        return new PlayerDTO(p.getId(), p.getName(), p.getNickHabbo(), p.getRole());
    }
}
