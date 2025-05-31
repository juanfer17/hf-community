package com.hfcommunity.hf_community_hub.standings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingRepository extends JpaRepository<Standing, Long> {
    List<Standing> findByTournamentIdAndModalityId(Long tournamentId, Long modalityId);
}
