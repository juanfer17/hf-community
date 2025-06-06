package com.hfcommunity.hf_community_hub.modality;

import com.hfcommunity.hf_community_hub.tournament.TournamentDTO;

import java.util.List;

public interface ModalityService {

    /**
     * Retorna todas las modalidades disponibles.
     */
    List<ModalityDTO> getAllModalities();

    /**
     * Retorna las modalidades en las que un jugador tiene el rol de árbitro.
     *
     * @param playerId ID del jugador (árbitro)
     * @return lista de modalidades donde el jugador tiene rol de árbitro
     */
    List<ModalityDTO> getModalitiesByRefereeId(Long playerId);

    /**
     * Retorna los torneos asociados a una modalidad.
     *
     * @param modalityId ID de la modalidad
     * @return lista de torneos asociados
     */
    List<TournamentDTO> getTournamentsByModality(Long modalityId);
}
