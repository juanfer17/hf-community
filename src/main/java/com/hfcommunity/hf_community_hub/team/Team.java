package com.hfcommunity.hf_community_hub.team;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.playerteam.PlayerTeam;
import com.hfcommunity.hf_community_hub.tournament.Tournament;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "equipos")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = true, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "torneo_id", nullable = false)
    private Tournament tournament;

    @Column(name = "logo_url", nullable = true)
    private String logoUrl;

    @OneToMany(mappedBy = "team")
    private List<PlayerTeam> playerTeams;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modalidad_id")
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "dt_id")
    private Player headCoach;
}
