package com.hfcommunity.hf_community_hub.offer;

import com.hfcommunity.hf_community_hub.callup.CallUpRepository;
import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRoleRepository;
import com.hfcommunity.hf_community_hub.playerteam.PlayerTeam;
import com.hfcommunity.hf_community_hub.playerteam.PlayerTeamId;
import com.hfcommunity.hf_community_hub.playerteam.PlayerTeamRepository;
import com.hfcommunity.hf_community_hub.team.Team;
import com.hfcommunity.hf_community_hub.team.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final CallUpRepository callUpRepository;
    private final PlayerTeamRepository playerTeamRepository;
    private final ModalityRepository modalityRepository;
    private final OfferMapper offerMapper;
    private final PlayerModalityRoleRepository playerModalityRoleRepository;

    public void createOffer(OfferRequest request, Long modalityId) {

        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new EntityNotFoundException("Modalidad no encontrada"));

        Player coach = playerRepository.findById(request.getCoachId())
                .orElseThrow(() -> new NoSuchElementException("DT no encontrado"));

        boolean isDTInModality = playerModalityRoleRepository
                .findByPlayerIdAndModalityId(coach.getId(), modalityId).stream()
                .anyMatch(r -> "DT".equalsIgnoreCase(r.getRole().getName()));

        if (!isDTInModality) {
            throw new IllegalArgumentException("Solo un DT puede hacer ofertas en esta modalidad");
        }

        Player player = playerRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new NoSuchElementException("Jugador no encontrado"));

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new NoSuchElementException("Equipo no encontrado"));

        if (team.getTournament() == null || team.getTournament().getModality() == null) {
            throw new IllegalStateException("El equipo no tiene torneo o modalidad asociada");
        }

        if (!team.getTournament().getModality().getId().equals(modalityId)) {
            throw new IllegalArgumentException("La modalidad del equipo no coincide con la modalidad proporcionada");
        }

        Offer offer = new Offer();
        offer.setCoach(coach);
        offer.setPlayer(player);
        offer.setTeam(team);
        offer.setModality(modality);
        offer.setCreatedAt(LocalDateTime.now());

        offerRepository.save(offer);
    }


    public List<OfferDTO> getOffersByModality(Long playerId, Long modalityId) {
        callUpRepository.findByPlayerIdAndModalityId(playerId, modalityId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontraron convocatorias en esta modalidad para este jugador"));

        return offerRepository
                .findByPlayerIdAndTeamTournamentModalityId(playerId, modalityId)
                .stream()
                .map(offerMapper::toDto)
                .toList();
    }

    @Transactional
    public void acceptOffer(OfferAcceptRequest request, Long modalityId) {
        Offer offer = offerRepository.findById(request.getOfferId())
                .orElseThrow(() -> new NoSuchElementException("Oferta no encontrada"));

        if (offer.getTeam() == null || offer.getTeam().getTournament() == null) {
            throw new IllegalStateException("La oferta no está asociada a un equipo con torneo válido");
        }

        Long playerId = offer.getPlayer().getId();

        boolean alreadyInTeam = playerTeamRepository.existsByPlayerIdAndTeamTournamentModalityId(playerId, modalityId);

        if (alreadyInTeam) {
            throw new IllegalStateException("El jugador ya está en un equipo de esta modalidad");
        }

        PlayerTeamId playerTeamId = new PlayerTeamId();
        playerTeamId.setPlayerId(offer.getTeam().getId());
        playerTeamId.setTeamId(offer.getTeam().getId());

        PlayerTeam playerTeam = new PlayerTeam();
        playerTeam.setId(playerTeamId);
        playerTeam.setPlayer(offer.getPlayer());
        playerTeam.setTeam(offer.getTeam());
        playerTeam.setModality(offer.getModality());
        playerTeamRepository.save(playerTeam);

        callUpRepository.deleteByPlayerIdAndModalityId(playerId, modalityId);
        offerRepository.deleteByPlayerIdAndTeamTournamentModalityId(playerId, modalityId);
    }
}