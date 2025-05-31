package com.hfcommunity.hf_community_hub.playerstatistic;

import com.hfcommunity.hf_community_hub.match.Match;
import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.tournament.Tournament;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "estadisticas_jugador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"player", "match", "modality"})
public class PlayerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jugador_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partido_id", nullable = false)
    private Match match;

    @Min(0)
    @Column(name = "goles")
    private Integer goals;

    @Min(0)
    @Column(name = "asistencias")
    private Integer assists;

    @Min(0)
    @Column(name = "autogoles")
    private Integer ownGoals;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modalidad_id", nullable = false)
    private Modality modality;

    @Min(0)
    @Column(name = "mvps")
    private Integer mvpCount;

    @Min(0)
    @Column(name = "menciones")
    private Integer mentionsCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "torneo_id", nullable = false)
    private Tournament tournament;
}
