package com.hfcommunity.hf_community_hub.playerstatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatistics, Long> {
    List<PlayerStatistics> findTop10ByMatch_Tournament_IdAndModality_IdOrderByGoalsDesc(Long tournamentId, Long modalityId);
    List<PlayerStatistics> findTop10ByMatch_Tournament_IdAndModality_IdOrderByAssistsDesc(Long tournamentId, Long modalityId);
    List<PlayerStatistics> findTop10ByMatch_Tournament_IdAndModality_IdOrderByOwnGoalsDesc(Long tournamentId, Long modalityId);


    @Query("""
        SELECT new com.hfcommunity.hf_community_hub.playerstatistic.PlayerStatisticsDTO(
            ps.player.id,
            ps.player.nickHabbo,
            ps.player.name,
            SUM(ps.goals),
            SUM(ps.assists),
            SUM(ps.ownGoals),
            SUM(ps.mvpCount),
            SUM(ps.mentionsCount)
        )
        FROM PlayerStatistics ps
        WHERE ps.tournament.id = :tournamentId
          AND ps.modality.id = :modalityId
        GROUP BY ps.player.id, ps.player.nickHabbo, ps.player.name
        ORDER BY SUM(ps.mvpCount) DESC
    """)
    List<PlayerStatisticsDTO> findTop10ByTournamentIdAndModalityIdOrderBySumMvpsDesc(Long tournamentId, Long modalityId);

    @Query("""
        SELECT new com.hfcommunity.hf_community_hub.playerstatistic.PlayerStatisticsDTO(
            ps.player.id,
            ps.player.nickHabbo,
            ps.player.name,
            SUM(ps.goals),
            SUM(ps.assists),
            SUM(ps.ownGoals),
            SUM(ps.mvpCount),
            SUM(ps.mentionsCount)
        )
        FROM PlayerStatistics ps
        WHERE ps.tournament.id = :tournamentId
          AND ps.modality.id = :modalityId
        GROUP BY ps.player.id, ps.player.nickHabbo, ps.player.name
        ORDER BY SUM(ps.mentionsCount) DESC
    """)
    List<PlayerStatisticsDTO> findTop10ByTournamentIdAndModalityIdOrderBySumMentionsDesc(Long tournamentId, Long modalityId);

}
