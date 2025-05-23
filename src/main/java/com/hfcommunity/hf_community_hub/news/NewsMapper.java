package com.hfcommunity.hf_community_hub.news;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsDTO toDto(News entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicationDate", ignore = true)
    News toEntity(NewsRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicationDate", ignore = true)
    void updateEntityFromRequest(NewsRequest request, @MappingTarget News entity);

    default Modality map(Long id) {
        if (id == null) return null;
        Modality modality = new Modality();
        modality.setId(id);
        return modality;
    }
}
