package com.hfcommunity.hf_community_hub.modality;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModalityMapper {
    ModalityDTO toDto(Modality entity);
    List<ModalityDTO> toDtoList(List<Modality> entities);
}
