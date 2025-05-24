package com.hfcommunity.hf_community_hub.match;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.team.Team;
import com.hfcommunity.hf_community_hub.tournament.Tournament;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "partidos")
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "torneo_id", nullable = false)
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_a_id", nullable = false)
    private Team teamA;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_b_id", nullable = false)
    private Team teamB;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "estado", nullable = false)
    private String state = "finished";

    @Column(name = "juez")
    private String referee;

    @Column(name = "goles_equipo_a", nullable = false)
    private Integer goalsTeamA = 0;

    @Column(name = "goles_equipo_b", nullable = false)
    private Integer goalsTeamB = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mvp_id")
    private Player mvp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mencion_equipo_a_id")
    private Player mentionTeamA;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mencion_equipo_b_id")
    private Player mentionTeamB;

    @Column(name = "link_video")
    private String videoLink;

    @Column(name = "observaciones")
    private String observations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modalidad_id", nullable = false)
    private Modality modality;
}