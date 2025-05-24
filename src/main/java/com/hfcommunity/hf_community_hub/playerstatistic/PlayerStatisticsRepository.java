package com.hfcommunity.hf_community_hub.playerstatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatistics, Long> {
    List<PlayerStatistics> findTop10ByMatch_Tournament_IdAndModality_IdOrderByGoalsDesc(Long tournamentId, Long modalityId);
    List<PlayerStatistics> findTop10ByMatch_Tournament_IdAndModality_IdOrderByAssistsDesc(Long tournamentId, Long modalityId);
    List<PlayerStatistics> findTop10ByMatch_Tournament_IdAndModality_IdOrderByOwnGoalsDesc(Long tournamentId, Long modalityId);
}
