package com.hfcommunity.hf_community_hub.standings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandingsDTO {
    private Long teamId;
    private String teamName;
    private int matchesPlayed;
    private int matchesWon;
    private int matchesDrawn;
    private int matchesLost;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int points;
}
