package com.hfcommunity.hf_community_hub.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OfferDTO {
    private Long id;
    private Long coachId;
    private String coachName;
    private Long teamId;
    private String teamName;
    private String modality;
}
