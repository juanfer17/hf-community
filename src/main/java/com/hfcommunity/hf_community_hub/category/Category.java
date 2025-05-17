package com.hfcommunity.hf_community_hub.category;

import com.hfcommunity.hf_community_hub.modality.Modality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "modalidad_id", nullable = false)
    private Modality modality;
}
