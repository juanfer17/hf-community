package com.hfcommunity.hf_community_hub.standings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandingMapper {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    StandingsDTO toDto(Standing entity);
}
