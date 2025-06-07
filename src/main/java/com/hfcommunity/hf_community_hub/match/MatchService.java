package com.hfcommunity.hf_community_hub.match;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.playerstatistic.PlayerStatistics;
import com.hfcommunity.hf_community_hub.playerstatistic.PlayerStatisticsRepository;
import com.hfcommunity.hf_community_hub.standings.Standing;
import com.hfcommunity.hf_community_hub.standings.StandingRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ModalityRepository modalityRepository;
    private final MatchMapper matchMapper;
    private final PlayerStatisticsRepository playerStatisticsRepository;
    private final StandingRepository standingRepository;

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
        match.setState("finished");
        match.setReferee(dto.getReferee());
        match.setGoalsTeamA(dto.getGoalsTeamA());
        match.setGoalsTeamB(dto.getGoalsTeamB());
        match.setMvp(mvp);
        match.setMentionTeamA(mentionA);
        match.setMentionTeamB(mentionB);
        match.setVideoLink(dto.getVideoLink());
        match.setObservations(dto.getObservations());

        Match savedMatch = matchRepository.save(match);

        // Update player statistics
        updatePlayerStatistics(savedMatch, mvp, mentionA, mentionB, dto.getPlayerStatistics());

        // Update standings
        updateStandings(savedMatch);

        return matchMapper.toDto(savedMatch);
    }

    public List<MatchDTO> getAllMatchesByModality(Long modalityId, Long tournamentId) {
        Modality modality = modalityRepository.findById(modalityId)
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no encontrada"));

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado para la modalidad '" + modality.getName() + "'"));

        return matchRepository.findByModalityAndTournamentOrderByDateDesc(modality, tournament).stream()
                .map(matchMapper::toDto)
                .toList();
    }

    /**
     * Updates player statistics for a match
     */
    private void updatePlayerStatistics(Match match, Player mvp, Player mentionTeamA, Player mentionTeamB, List<PlayerStatisticsCreateDTO> playerStatistics) {
        // Create statistics for MVP
        if (mvp != null) {
            createOrUpdatePlayerStatistics(mvp, match, 0, 0, 0, 1, 0);
        }

        // Create statistics for mention team A
        if (mentionTeamA != null) {
            createOrUpdatePlayerStatistics(mentionTeamA, match, 0, 0, 0, 0, 1);
        }

        // Create statistics for mention team B
        if (mentionTeamB != null) {
            createOrUpdatePlayerStatistics(mentionTeamB, match, 0, 0, 0, 0, 1);
        }

        // Process player statistics from DTO
        if (playerStatistics != null && !playerStatistics.isEmpty()) {
            for (PlayerStatisticsCreateDTO playerStat : playerStatistics) {
                Player player = playerRepository.findById(playerStat.getPlayerId())
                        .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

                createOrUpdatePlayerStatistics(
                    player, 
                    match, 
                    playerStat.getGoals() != null ? playerStat.getGoals() : 0, 
                    playerStat.getAssists() != null ? playerStat.getAssists() : 0, 
                    playerStat.getOwnGoals() != null ? playerStat.getOwnGoals() : 0, 
                    0, 
                    0
                );
            }
        }
    }

    /**
     * Creates or updates player statistics for a match
     */
    private void createOrUpdatePlayerStatistics(Player player, Match match, int goals, int assists, int ownGoals, int mvpCount, int mentionsCount) {
        // Check if player already has statistics for this match
        Optional<PlayerStatistics> existingStats = playerStatisticsRepository.findByPlayerAndMatch(player, match);

        PlayerStatistics stats;
        if (existingStats.isPresent()) {
            // Update existing statistics
            stats = existingStats.get();
            stats.setGoals(stats.getGoals() + goals);
            stats.setAssists(stats.getAssists() + assists);
            stats.setOwnGoals(stats.getOwnGoals() + ownGoals);
            stats.setMvpCount(stats.getMvpCount() + mvpCount);
            stats.setMentionsCount(stats.getMentionsCount() + mentionsCount);
        } else {
            // Create new statistics
            stats = new PlayerStatistics();
            stats.setPlayer(player);
            stats.setMatch(match);
            stats.setModality(match.getModality());
            stats.setTournament(match.getTournament());
            stats.setGoals(goals);
            stats.setAssists(assists);
            stats.setOwnGoals(ownGoals);
            stats.setMvpCount(mvpCount);
            stats.setMentionsCount(mentionsCount);
        }

        playerStatisticsRepository.save(stats);
    }

    /**
     * Updates standings for a match
     */
    private void updateStandings(Match match) {
        // Update standings for team A
        updateTeamStanding(match.getTeamA(), match.getTournament(), match.getModality(), 
                match.getGoalsTeamA(), match.getGoalsTeamB());

        // Update standings for team B
        updateTeamStanding(match.getTeamB(), match.getTournament(), match.getModality(), 
                match.getGoalsTeamB(), match.getGoalsTeamA());
    }

    /**
     * Updates standings for a team
     */
    private void updateTeamStanding(Team team, Tournament tournament, Modality modality, 
                                   Integer goalsFor, Integer goalsAgainst) {
        // Find existing standing or create new one
        Optional<Standing> standingOpt = standingRepository.findByTournamentIdAndModalityId(
                tournament.getId(), modality.getId()).stream()
                .filter(s -> s.getTeam().getId().equals(team.getId()))
                .findFirst();

        Standing standing;
        if (standingOpt.isPresent()) {
            standing = standingOpt.get();
        } else {
            standing = new Standing();
            standing.setTeam(team);
            standing.setTournament(tournament);
            standing.setModality(modality);
            standing.setMatchesPlayed(0);
            standing.setMatchesWon(0);
            standing.setMatchesDrawn(0);
            standing.setMatchesLost(0);
            standing.setGoalsFor(0);
            standing.setGoalsAgainst(0);
            standing.setGoalDifference(0);
            standing.setPoints(0);
        }

        // Update standing
        standing.setMatchesPlayed(standing.getMatchesPlayed() + 1);
        standing.setGoalsFor(standing.getGoalsFor() + goalsFor);
        standing.setGoalsAgainst(standing.getGoalsAgainst() + goalsAgainst);
        standing.setGoalDifference(standing.getGoalsFor() - standing.getGoalsAgainst());

        // Update match result (win, draw, loss)
        if (goalsFor > goalsAgainst) {
            standing.setMatchesWon(standing.getMatchesWon() + 1);
            standing.setPoints(standing.getPoints() + 3); // 3 points for a win
        } else if (goalsFor.equals(goalsAgainst)) {
            standing.setMatchesDrawn(standing.getMatchesDrawn() + 1);
            standing.setPoints(standing.getPoints() + 1); // 1 point for a draw
        } else {
            standing.setMatchesLost(standing.getMatchesLost() + 1);
            // 0 points for a loss
        }

        standingRepository.save(standing);
    }
}
