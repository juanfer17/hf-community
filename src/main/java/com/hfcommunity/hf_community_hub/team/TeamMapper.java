package com.hfcommunity.hf_community_hub.team;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "tournament.modality", target = "tournamentModality", qualifiedByName = "modalityToString")
    TeamDTO toDto(Team team);

    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "tournament.modality", target = "tournamentModality", qualifiedByName = "modalityToString")
    @Mapping(source = "logoUrl", target = "logoUrl")
    TeamDTO toDtoWithLogo(Team team);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "tournament.modality", target = "tournamentModality", qualifiedByName = "modalityToString")
    TeamDTO toSimpleDto(Team team);

    @Named("modalityToString")
    default String mapModalityToString(Modality modality) {
        return modality != null ? modality.getName() : null;
    }
}