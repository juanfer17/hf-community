package com.hfcommunity.hf_community_hub.playerteam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerTeamDTO {
    private Long playerId;
    private Long teamId;
    private String modality;
}
