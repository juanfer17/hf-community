package com.hfcommunity.hf_community_hub.playerteam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerTeamMapper {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "player.nickHabbo", target = "nickHabbo")
    PlayerTeamDTO toDto(PlayerTeam playerTeam);
}
