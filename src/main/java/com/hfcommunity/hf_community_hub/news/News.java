package com.hfcommunity.hf_community_hub.news;

import com.hfcommunity.hf_community_hub.modality.Modality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "noticia")
@Getter
@Setter
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String title;

    @Column(name = "contenido", columnDefinition = "TEXT")
    private String content;

    @Column(name = "fecha_publicacion")
    private LocalDateTime publicationDate;

    @Column(name = "imagenes_urls")
    private String imageUrls;

    @ManyToOne
    @JoinColumn(name = "modalidad_id")
    private Modality modality;
}
