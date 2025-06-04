package com.hfcommunity.hf_community_hub.playermodalityrol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerModalityRoleRepository extends JpaRepository<PlayerModalityRole, Long> {
    List<PlayerModalityRole> findByPlayerId(Long playerId);
    List<PlayerModalityRole> findByPlayerIdAndModalityId(Long playerId, Long modalityId);
}
