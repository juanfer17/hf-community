package com.hfcommunity.hf_community_hub.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);
    Optional<Player> findByNickHabbo(String nickhabbo);
    Optional<Player> findByIdAndRole(Long id, String role);
}
