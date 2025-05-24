package com.hfcommunity.hf_community_hub.callup;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CallUpMapper {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "player.nickHabbo", target = "nickhabbo")
    @Mapping(source = "modality.name", target = "modality")
    CallUpDTO toDto(CallUp callUp);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "player", ignore = true)
    @Mapping(target = "modality", ignore = true)
    CallUp toEntity(CallUpRequest request);
}
