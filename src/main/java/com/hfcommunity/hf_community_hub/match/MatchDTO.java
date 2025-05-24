package com.hfcommunity.hf_community_hub.match;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MatchDTO {
    private Long id;
    private Long tournamentId;
    private Long teamAId;
    private Long teamBId;
    private LocalDateTime date;
    private String state;
    private String referee;
    private Integer goalsTeamA;
    private Integer goalsTeamB;
    private Long mvpId;
    private Long mentionTeamAId;
    private Long mentionTeamBId;
    private String videoLink;
    private String observations;
}