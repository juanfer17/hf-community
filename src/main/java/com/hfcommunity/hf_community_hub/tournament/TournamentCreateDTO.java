package com.hfcommunity.hf_community_hub.tournament;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La modalidad es obligatoria")
    private String modality;

    @NotBlank(message = "El formato es obligatorio")
    private String format;
}
