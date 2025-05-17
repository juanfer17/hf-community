package com.hfcommunity.hf_community_hub.team;

import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private Long tournamentId;
    private String tournamentModality;
    private String logoUrl;
}