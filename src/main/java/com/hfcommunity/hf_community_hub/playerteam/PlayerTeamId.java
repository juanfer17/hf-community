package com.hfcommunity.hf_community_hub.playerteam;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerTeamId that)) return false;
        return Objects.equals(playerId, that.playerId) &&
                Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, teamId);
    }
}
