package com.hfcommunity.hf_community_hub.match;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "teamA.id", target = "teamAId")
    @Mapping(source = "teamB.id", target = "teamBId")
    @Mapping(source = "mvp.id", target = "mvpId")
    @Mapping(source = "mentionTeamA.id", target = "mentionTeamAId")
    @Mapping(source = "mentionTeamB.id", target = "mentionTeamBId")
    MatchDTO toDto(Match match);
}
