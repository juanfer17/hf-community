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
    private int totalMvpCount;
    private int totalMentionsCount;

    public PlayerStatisticsDTO(Long playerId, String nickHabbo, String name,
                               Long totalGoals, Long totalAssists, Long totalOwnGoals,
                               Long totalMvpCount, Long totalMentionsCount) {
        this.playerId = playerId;
        this.nickHabbo = nickHabbo;
        this.name = name;
        this.totalGoals = totalGoals != null ? totalGoals.intValue() : 0;
        this.totalAssists = totalAssists != null ? totalAssists.intValue() : 0;
        this.totalOwnGoals = totalOwnGoals != null ? totalOwnGoals.intValue() : 0;
        this.totalMvpCount = totalMvpCount != null ? totalMvpCount.intValue() : 0;
        this.totalMentionsCount = totalMentionsCount != null ? totalMentionsCount.intValue() : 0;
    }
}
