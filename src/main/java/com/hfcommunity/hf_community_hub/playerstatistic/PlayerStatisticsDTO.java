package com.hfcommunity.hf_community_hub.playerstatistic;

import lombok.Data;

@Data
public class PlayerStatisticsDTO {
    private Long playerId;
    private String nickHabbo;
    private String name;
    private int totalGoals;
    private int totalAssists;
    private int totalOwnGoals;
}
