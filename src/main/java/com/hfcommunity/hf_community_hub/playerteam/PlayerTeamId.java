package com.hfcommunity.hf_community_hub.playerteam;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerTeamId implements Serializable {
    @Column(name = "jugador_id")
    private Long playerId;

    @Column(name = "equipo_id")
    private Long teamId;
}
