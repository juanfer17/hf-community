package com.hfcommunity.hf_community_hub.playerstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerStatisticsService {

    private final PlayerStatisticsRepository repository;
    private final PlayerStatisticsMapper mapper;

    public List<PlayerStatisticsDTO> getTopScorersByTournamentAndModality(Long tournamentId, Long modalityId) {
        return repository
                .findTop10ByMatch_Tournament_IdAndModality_IdOrderByGoalsDesc(tournamentId, modalityId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<PlayerStatisticsDTO> getTopAssistProviders(Long tournamentId, Long modalityId) {
        return repository
                .findTop10ByMatch_Tournament_IdAndModality_IdOrderByAssistsDesc(tournamentId, modalityId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<PlayerStatisticsDTO> getTopOwnGoalScorers(Long tournamentId, Long modalityId) {
        return repository
                .findTop10ByMatch_Tournament_IdAndModality_IdOrderByOwnGoalsDesc(tournamentId, modalityId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }


    public List<PlayerStatisticsDTO> getTopMvps(Long tournamentId, Long modalityId) {
        return repository.findTop10ByTournamentIdAndModalityIdOrderBySumMvpsDesc(tournamentId, modalityId);
    }

    public List<PlayerStatisticsDTO> getTopMentions(Long tournamentId, Long modalityId) {
        return repository.findTop10ByTournamentIdAndModalityIdOrderBySumMentionsDesc(tournamentId, modalityId);
    }

}
