package com.hfcommunity.hf_community_hub.playerstatistic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PlayerStatisticsMapper {

    @Mappings({
            @Mapping(source = "player.id", target = "playerId"),
            @Mapping(source = "player.nickHabbo", target = "nickHabbo"),
            @Mapping(source = "player.name", target = "name"),
            @Mapping(source = "goals", target = "totalGoals"),
            @Mapping(source = "assists", target = "totalAssists"),
            @Mapping(source = "ownGoals", target = "totalOwnGoals")
    })
    PlayerStatisticsDTO toDTO(PlayerStatistics stats);
}