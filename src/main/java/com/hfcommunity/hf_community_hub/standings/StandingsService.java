package com.hfcommunity.hf_community_hub.standings;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final StandingRepository standingRepository;
    private final StandingMapper standingMapper;

    public List<StandingsDTO> getStandings(Long tournamentId, Long modalityId) {
        return standingRepository.findByTournamentIdAndModalityId(tournamentId, modalityId).stream()
                .map(standingMapper::toDto)
                .sorted((s1, s2) -> {
                    int cmpPoints = Integer.compare(s2.getPoints(), s1.getPoints());
                    if (cmpPoints != 0) return cmpPoints;
                    return Integer.compare(s2.getGoalDifference(), s1.getGoalDifference());
                })
                .collect(Collectors.toList());
    }
}
