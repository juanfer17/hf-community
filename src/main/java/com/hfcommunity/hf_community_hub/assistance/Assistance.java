package com.hfcommunity.hf_community_hub.assistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "asistencia")
@Getter
@Setter
public class Assistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column(name = "ip")
    private String ip;
}
