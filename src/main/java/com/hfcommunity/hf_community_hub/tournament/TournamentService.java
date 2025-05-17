package com.hfcommunity.hf_community_hub.tournament;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final ModalityRepository modalityRepository;
    private final TournamentMapper tournamentMapper;

    private Modality findModalityById(Long modalityId) {
        return modalityRepository.findById(modalityId)
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no encontrada con id: " + modalityId));
    }

    @Transactional
    public Map<String, Object> createTournament(Long modalityId, TournamentCreateDTO dto) {
        Modality modality = findModalityById(modalityId);

        Tournament tournament = new Tournament();
        tournament.setName(dto.getName());
        tournament.setModality(modality);
        tournament.setFormat(dto.getFormat());
        tournament.setCreatedAt(LocalDateTime.now());

        Tournament savedTournament = tournamentRepository.save(tournament);

        return Map.of(
                "message", "Torneo creado exitosamente",
                "tournament", tournamentMapper.toDto(savedTournament)
        );
    }

    public List<TournamentDTO> getAllTournamentsByModality(Long modalityId) {
        Modality modality = findModalityById(modalityId);

        return tournamentRepository.findByModality(modality).stream()
                .map(tournamentMapper::toDto)
                .collect(Collectors.toList());
    }

    public TournamentDTO getTournamentByIdAndModality(Long tournamentId, Long modalityId) {
        Modality modality = findModalityById(modalityId);

        Tournament tournament = tournamentRepository.findByIdAndModality_Id(tournamentId, modality.getId())
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado con id: " + tournamentId));

        return tournamentMapper.toDto(tournament);
    }

    @Transactional
    public void deleteTournament(Long tournamentId, Long modalityId) {
        Modality modality = findModalityById(modalityId);

        Tournament tournament = tournamentRepository.findByIdAndModality_Id(tournamentId, modality.getId())
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado con id: " + tournamentId));

        tournamentRepository.delete(tournament);
    }
}
