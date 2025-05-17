package com.hfcommunity.hf_community_hub.playerteam;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PlayerTeamMapper {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "modality", target = "modality", qualifiedByName = "modalityToString")
    PlayerTeamDTO toDto(PlayerTeam playerTeam);

    @Named("modalityToString")
    default String mapModalityToString(Modality modality) {
        return modality != null ? modality.getName() : null;
    }
}
