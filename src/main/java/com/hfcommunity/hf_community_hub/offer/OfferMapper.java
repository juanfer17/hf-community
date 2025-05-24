package com.hfcommunity.hf_community_hub.offer;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source = "coach.id", target = "coachId")
    @Mapping(source = "coach.nickHabbo", target = "coachName")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "team.tournament.modality", target = "modality", qualifiedByName = "modalityToString")
    OfferDTO toDto(Offer offer);

    @Named("modalityToString")
    default String mapModalityToString(Modality modality) {
        return modality != null ? modality.getName() : null;
    }
}