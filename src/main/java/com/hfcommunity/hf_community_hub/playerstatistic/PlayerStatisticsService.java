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
        return repository.findTop10ScorersByTournamentIdAndModalityId(tournamentId, modalityId);
    }

    public List<PlayerStatisticsDTO> getTopAssistProviders(Long tournamentId, Long modalityId) {
        return repository.findTop10AssistantsByTournamentIdAndModalityId(tournamentId, modalityId);
    }

    public List<PlayerStatisticsDTO> getTopOwnGoalScorers(Long tournamentId, Long modalityId) {
        return repository.findTop10OwnGoalsByTournamentIdAndModalityId(tournamentId, modalityId);
    }


    public List<PlayerStatisticsDTO> getTopMvps(Long tournamentId, Long modalityId) {
        return repository.findTop10ByTournamentIdAndModalityIdOrderBySumMvpsDesc(tournamentId, modalityId);
    }

    public List<PlayerStatisticsDTO> getTopMentions(Long tournamentId, Long modalityId) {
        return repository.findTop10ByTournamentIdAndModalityIdOrderBySumMentionsDesc(tournamentId, modalityId);
    }

}
