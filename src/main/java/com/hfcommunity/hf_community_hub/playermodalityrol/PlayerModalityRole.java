package com.hfcommunity.hf_community_hub.playermodalityrol;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.role.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jugador_modalidad_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerModalityRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "modalidad_id", nullable = false)
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Role role;
}
