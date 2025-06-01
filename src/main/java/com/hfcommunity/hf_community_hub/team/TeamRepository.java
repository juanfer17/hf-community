package com.hfcommunity.hf_community_hub.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByModality_IdAndTournament_Id(Long modalityId, Long tournamentId);

    List<Team> findByLogoUrlIsNotNullAndModality_Id(Long modality);

    List<Team> findByHeadCoachIdAndModalityId(Long playerId, Long modalityId);

    List<Team> findByTournamentIdAndModality_Id(Long tournamentId, Long modalityId);


}
