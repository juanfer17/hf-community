package com.hfcommunity.hf_community_hub.team;

import lombok.Data;

@Data
public class TeamCreateDTO {
    private String name;
    private Long tournamentId;
    private Long categoryId;
    private Long dtId;
    private String logo;
}

