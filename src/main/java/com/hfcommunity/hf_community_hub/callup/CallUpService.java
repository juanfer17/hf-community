package com.hfcommunity.hf_community_hub.callup;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CallUpService {

    private final CallUpRepository repository;
    private final PlayerRepository playerRepository;
    private final ModalityRepository modalityRepository;
    private final CallUpMapper mapper;

    public CallUpDTO create(Long modalityId, CallUpRequest request) {
        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada con ID: " + modalityId));

        repository.findByPlayerIdAndModalityId(request.getPlayerId(), modalityId)
                .ifPresent(c -> {
                    throw new RuntimeException("Ya tienes una convocatoria activa en esta modalidad");
                });

        Player player = playerRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        CallUp callUp = mapper.toEntity(request);
        callUp.setPlayer(player);
        callUp.setModality(modality);

        return mapper.toDto(repository.save(callUp));
    }

    public List<CallUpDTO> findByModality(Long modalityId) {
        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada con ID: " + modalityId));

        return repository.findByModalityId(modalityId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
