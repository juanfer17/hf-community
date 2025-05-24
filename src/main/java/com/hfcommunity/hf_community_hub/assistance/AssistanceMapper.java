package com.hfcommunity.hf_community_hub.assistance;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AssistanceMapper {

    @Mapping(target = "ip", source = "ip", qualifiedByName = "hideIpIfNotAdmin")
    AssistanceDTO toDto(Assistance assistance, @org.mapstruct.Context boolean isAdmin);

    @Named("hideIpIfNotAdmin")
    static String hideIpIfNotAdmin(String ip, @org.mapstruct.Context boolean isAdmin) {
        return isAdmin ? ip : null;
    }
}
