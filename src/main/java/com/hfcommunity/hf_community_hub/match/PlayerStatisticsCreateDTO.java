package com.hfcommunity.hf_community_hub.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStatisticsCreateDTO {
    private Long playerId;
    private Long teamId;
    private Integer goals;
    private Integer assists;
    private Integer ownGoals;
}