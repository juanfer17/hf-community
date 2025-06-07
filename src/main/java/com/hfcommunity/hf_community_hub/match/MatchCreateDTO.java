package com.hfcommunity.hf_community_hub.match;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchCreateDTO {

    private Long tournamentId;
    private Long teamAId;
    private Long teamBId;
    private String referee;
    private Integer goalsTeamA;
    private Integer goalsTeamB;
    private Long mvpId;
    private Long mentionTeamAId;
    private Long mentionTeamBId;
    private String videoLink;
    private String observations;
    private List<PlayerStatisticsCreateDTO> playerStatistics;
}
