package com.hfcommunity.hf_community_hub.player;

import com.hfcommunity.hf_community_hub.playerteam.PlayerTeam;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "salt", nullable = true)
    private String salt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_registered", nullable = false)
    private Boolean isRegistered = false;

    @Column(name = "nickhabbo", nullable = false, unique = true)
    private String nickHabbo;

    @Column(name = "role", nullable = false)
    private String role = "player";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerTeam> playerTeams;

    public Boolean isSuperAdmin() {
        return this.role.equals("superadmin");
    }

    public Boolean isAdmin() {
        return this.role.equals("admin");
    }

    public Boolean isCoach() {
        return this.role.equals("coach");
    }
}
