package com.hfcommunity.hf_community_hub.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCreateDTO {
    private String email;
    private String name;
    private String password;
    private String nickhabbo;
}
