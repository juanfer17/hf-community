package com.hfcommunity.hf_community_hub.callup;


import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.player.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "convocatorias")
@Getter
@Setter
public class CallUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Player player;

    @Column(name = "mensaje", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modalidad_id", nullable = false)
    private Modality modality;
}

