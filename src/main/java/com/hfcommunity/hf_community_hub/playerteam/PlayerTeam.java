package com.hfcommunity.hf_community_hub.playerteam;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jugadores_equipos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerTeam {

    @EmbeddedId
    private PlayerTeamId id;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "jugador_id")
    private Player player;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "equipo_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modalidad_id", nullable = false)
    private Modality modality;
}
