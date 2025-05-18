package com.hfcommunity.hf_community_hub.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByModality_Id(Long modalityId);

    List<Team> findByLogoUrlIsNotNullAndModality_Id_NameIgnoreCase(Long modality);

    List<Team> findByHeadCoachId(Long playerId);

    List<Team> findByTournamentIdAndModality_Id(Long tournamentId, Long modalityId);

}
