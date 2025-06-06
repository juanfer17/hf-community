package com.hfcommunity.hf_community_hub.modality;

import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRole;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRoleRepository;
import com.hfcommunity.hf_community_hub.tournament.TournamentDTO;
import com.hfcommunity.hf_community_hub.tournament.TournamentMapper;
import com.hfcommunity.hf_community_hub.tournament.TournamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ModalityServiceImpl implements ModalityService {

    private final ModalityRepository modalityRepository;
    private final PlayerRepository playerRepository;
    private final PlayerModalityRoleRepository playerModalityRoleRepository;
    private final TournamentRepository tournamentRepository;
    private final ModalityMapper modalityMapper;
    private final TournamentMapper tournamentMapper;

    @Override
    public List<ModalityDTO> getAllModalities() {
        return modalityMapper.toDtoList(modalityRepository.findAll());
    }


    @Override
    public List<TournamentDTO> getTournamentsByModality(Long modalityId) {
        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no encontrada"));
        return tournamentMapper.toDtoList(
                tournamentRepository.findByModality(modality)
        );
    }

    @Override
    public List<ModalityDTO> getModalitiesByRefereeId(Long playerId) {
        List<PlayerModalityRole> roles = playerModalityRoleRepository.findByPlayer_IdAndRole_NameIgnoreCase(playerId, "ARBITRO");

        Set<Modality> modalities = roles.stream()
                .map(PlayerModalityRole::getModality)
                .collect(Collectors.toSet());

        return modalityMapper.toDtoList(new ArrayList<>(modalities));
    }
}
