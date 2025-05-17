package com.hfcommunity.hf_community_hub.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByModality_NameIgnoreCase(String modality);

    List<Team> findByLogoUrlIsNotNullAndModality_NameIgnoreCase(String modality);

    List<Team> findByTournamentIdAndModality_NameIgnoreCase(Long tournamentId, String modality);

    List<Team> findByHeadCoachId(Long playerId);
}
