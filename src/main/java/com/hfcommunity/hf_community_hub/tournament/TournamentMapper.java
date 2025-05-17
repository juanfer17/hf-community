package com.hfcommunity.hf_community_hub.tournament;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentMapper {

    @Mapping(source = "modality", target = "modality", qualifiedByName = "modalityToString")
    TournamentDTO toDto(Tournament tournament);

    List<TournamentDTO> toDtoList(List<Tournament> tournaments);

    @Named("modalityToString")
    default String mapModalityToString(Modality modality) {
        return modality != null ? modality.getName() : null;
    }
}