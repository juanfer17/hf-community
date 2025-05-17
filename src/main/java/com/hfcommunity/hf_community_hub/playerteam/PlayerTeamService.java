package com.hfcommunity.hf_community_hub.playerteam;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.team.Team;
import com.hfcommunity.hf_community_hub.team.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerTeamService {

    private final PlayerTeamRepository playerTeamRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ModalityRepository modalityRepository;
    private final PlayerTeamMapper playerTeamMapper;

    public void registerPlayerToTeam(Long playerId, Long teamId, String modalityName) {
        Modality modality = modalityRepository.findByNameIgnoreCase(modalityName)
                .orElseThrow(() -> new EntityNotFoundException("Modalidad no encontrada"));

        if (playerTeamRepository.existsByPlayerIdAndTeamIdAndModality(playerId, teamId, modality)) {
            throw new IllegalArgumentException("Jugador ya registrado en ese equipo y modalidad");
        }

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));

        PlayerTeam entity = new PlayerTeam();
        entity.setId(new PlayerTeamId(playerId, teamId));
        entity.setPlayer(player);
        entity.setTeam(team);
        entity.setModality(modality);

        playerTeamRepository.save(entity);
    }

    public List<PlayerTeamDTO> getPlayersByTeam(Long teamId, String modalityName) {
        Modality modality = modalityRepository.findByNameIgnoreCase(modalityName)
                .orElseThrow(() -> new EntityNotFoundException("Modalidad no encontrada"));

        return playerTeamRepository.findByTeamIdAndModality(teamId, modality)
                .stream()
                .map(playerTeamMapper::toDto)
                .toList();
    }

    public List<PlayerTeamDTO> getTeamsByPlayer(Long playerId, String modalityName) {
        Modality modality = modalityRepository.findByNameIgnoreCase(modalityName)
                .orElseThrow(() -> new EntityNotFoundException("Modalidad no encontrada"));

        return playerTeamRepository.findByPlayerIdAndModality(playerId, modality)
                .stream()
                .map(playerTeamMapper::toDto)
                .toList();
    }
}
