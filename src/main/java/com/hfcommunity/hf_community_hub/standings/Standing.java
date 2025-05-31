package com.hfcommunity.hf_community_hub.standings;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.team.Team;
import com.hfcommunity.hf_community_hub.tournament.Tournament;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posiciones")
@Getter
@Setter
public class Standing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "id_torneo", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "id_modalidad", nullable = false)
    private Modality modality;

    @Column(name = "partidos_jugados")
    private int matchesPlayed;

    @Column(name = "ganados")
    private int matchesWon;

    @Column(name = "empatados")
    private int matchesDrawn;

    @Column(name = "perdidos")
    private int matchesLost;

    @Column(name = "goles_a_favor")
    private int goalsFor;

    @Column(name = "goles_en_contra")
    private int goalsAgainst;

    @Column(name = "diferencia_goles")
    private int goalDifference;

    @Column(name = "puntos")
    private int points;

}
