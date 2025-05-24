package com.hfcommunity.hf_community_hub.match;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.team.Team;
import com.hfcommunity.hf_community_hub.team.TeamRepository;
import com.hfcommunity.hf_community_hub.tournament.Tournament;
import com.hfcommunity.hf_community_hub.tournament.TournamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ModalityRepository modalityRepository;
    private final MatchMapper matchMapper;

    @Transactional
    public MatchDTO createMatch(Long modalityId, MatchCreateDTO dto, Authentication auth) {

        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no encontrada"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado"));

        if (!tournament.getModality().getId().equals(modality.getId())) {
            throw new IllegalArgumentException("El torneo no pertenece a la modalidad '" + modality.getName() + "'");
        }

        Team teamA = teamRepository.findById(dto.getTeamAId())
                .orElseThrow(() -> new IllegalArgumentException("Equipo A no encontrado"));
        Team teamB = teamRepository.findById(dto.getTeamBId())
                .orElseThrow(() -> new IllegalArgumentException("Equipo B no encontrado"));

        if (!teamA.getModality().getId().equals(modality.getId()) || !teamB.getModality().getId().equals(modality.getId())) {
            throw new IllegalArgumentException("Ambos equipos deben pertenecer a la modalidad '" + modality.getName() + "'");
        }

        Player mvp = dto.getMvpId() != null ?
                playerRepository.findById(dto.getMvpId())
                        .orElseThrow(() -> new IllegalArgumentException("Jugador MVP no encontrado")) :
                null;

        Player mentionA = dto.getMentionTeamAId() != null ?
                playerRepository.findById(dto.getMentionTeamAId())
                        .orElseThrow(() -> new IllegalArgumentException("Jugador mencionado (Equipo A) no encontrado")) :
                null;

        Player mentionB = dto.getMentionTeamBId() != null ?
                playerRepository.findById(dto.getMentionTeamBId())
                        .orElseThrow(() -> new IllegalArgumentException("Jugador mencionado (Equipo B) no encontrado")) :
                null;

        Match match = new Match();
        match.setModality(modality);
        match.setTournament(tournament);
        match.setTeamA(teamA);
        match.setTeamB(teamB);
        match.setDate(LocalDateTime.now());
        match.setState("programado");
        match.setReferee(dto.getReferee());
        match.setGoalsTeamA(dto.getGoalsTeamA());
        match.setGoalsTeamB(dto.getGoalsTeamB());
        match.setMvp(mvp);
        match.setMentionTeamA(mentionA);
        match.setMentionTeamB(mentionB);
        match.setVideoLink(dto.getVideoLink());
        match.setObservations(dto.getObservations());

        Match savedMatch = matchRepository.save(match);
        return matchMapper.toDto(savedMatch);
    }

    public List<MatchDTO> getAllMatchesByModality(Long modalityId) {
        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no encontrada"));

        return matchRepository.findByModalityOrderByDateDesc(modality).stream()
                .map(matchMapper::toDto)
                .toList();
    }
}