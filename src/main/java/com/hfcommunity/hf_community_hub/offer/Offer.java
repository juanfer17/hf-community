package com.hfcommunity.hf_community_hub.offer;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.team.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ofertas")
@Getter
@Setter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dt_id", referencedColumnName = "id", nullable = false)
    private Player coach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jugador_id", referencedColumnName = "id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modalidad_id", nullable = false)
    private Modality modality;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
