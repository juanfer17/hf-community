package com.hfcommunity.hf_community_hub.playerteam;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerTeamRepository extends JpaRepository<PlayerTeam, PlayerTeamId> {

    @EntityGraph(attributePaths = {"player"})
    List<PlayerTeam> findByTeamIdAndModality(Long teamId, Modality modality);

    List<PlayerTeam> findByPlayerIdAndModality(Long playerId, Modality modality);

    boolean existsByPlayerIdAndTeamIdAndModality(Long playerId, Long teamId, Modality modality);
    boolean existsByPlayerIdAndTeamTournamentModalityId(Long playerId, Long modalityId);

}
