package com.hfcommunity.hf_community_hub.auth;

import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRole;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRoleRepository;
import com.hfcommunity.hf_community_hub.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final PlayerRepository playerRepo;
    private final PlayerModalityRoleRepository playerModalityRoleRepository;
    private final JwtTokenProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
        if (dto.getEmail() == null || dto.getName() == null || dto.getPassword() == null || dto.getNickHabbo() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Todos los datos tienen que estar completos"));
        }

        if (!isValidPassword(dto.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "La contraseña debe tener mínimo 8 caracteres, una mayúscula, una minúscula y un número."
            ));
        }

        if (playerRepo.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El jugador ya está registrado"));
        }

        String salt = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        byte[] derived = SCrypt.generate((dto.getPassword() + salt).getBytes(), salt.getBytes(), 32768, 8, 1, 64);
        String hash = "scrypt:32768:8:1$" + salt + "$" + Hex.toHexString(derived);

        Player player = new Player();
        player.setEmail(dto.getEmail());
        player.setName(dto.getName());
        player.setNickHabbo(dto.getNickHabbo());
        player.setSalt(salt);
        player.setPassword(hash);
        player.setIsActive(true);
        player.setIsRegistered(true);

        playerRepo.save(player);

        return ResponseEntity.status(201).body(Map.of("message", "Jugador registrado satisfactoriamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Player player = playerRepo.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String raw = loginDTO.getPassword() + player.getSalt();

        if (!verifyScryptPassword(raw, player.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }

        List<Map<String, Object>> roleList = playerModalityRoleRepository.findByPlayerId(player.getId()).stream()
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("modalityName", r.getModality().getName());
                    map.put("role", r.getRole().getName());
                    return map;
                })
                .collect(Collectors.toList()); // mutable list

        if (roleList.isEmpty()) {
            Map<String, Object> defaultRole = new HashMap<>();
            defaultRole.put("modalityName", null);
            defaultRole.put("role", "JUGADOR");

            roleList = new ArrayList<>();
            roleList.add(defaultRole);
        }

        String token = jwtProvider.generateToken(player.getId(), roleList);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "id", player.getId(),
                "roles", roleList,
                "nickHabbo", player.getNickHabbo()
        ));
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    private boolean verifyScryptPassword(String password, String storedHash) {
        try {
            if (!storedHash.startsWith("scrypt:")) return false;

            String[] parts = storedHash.split("\\$");
            String[] meta = parts[0].split(":");

            int N = Integer.parseInt(meta[1]);
            int r = Integer.parseInt(meta[2]);
            int p = Integer.parseInt(meta[3]);

            byte[] salt = parts[1].getBytes();
            byte[] hash = Hex.decode(parts[2]);

            byte[] derived = SCrypt.generate(password.getBytes(), salt, N, r, p, hash.length);

            return java.util.Arrays.equals(derived, hash);
        } catch (Exception e) {
            return false;
        }
    }
}
