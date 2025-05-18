package com.hfcommunity.hf_community_hub.team;


import com.hfcommunity.hf_community_hub.category.Category;
import com.hfcommunity.hf_community_hub.category.CategoryRepository;
import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.tournament.Tournament;
import com.hfcommunity.hf_community_hub.tournament.TournamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final CategoryRepository categoryRepository;
    private final ModalityRepository modalityRepository;
    private final TeamMapper teamMapper;
    private final PlayerRepository playerRepository;

    public TeamDTO createTeam(TeamCreateDTO dto, MultipartFile logo, Long modality) {

        Modality modalityEntity = modalityRepository.findById(modality)
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no encontrada"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Player player = playerRepository.findByIdAndRole(dto.getDtId(), "dt")
                .orElseThrow(() -> new IllegalArgumentException("Jugador no es DT"));


        if (category.getModality() == null || !category.getModality().getName().equalsIgnoreCase(modalityEntity.getName())) {
            throw new IllegalArgumentException("La modalidad proporcionada no coincide con la modalidad de la categoría.");
        }

        Team team = new Team();
        team.setName(dto.getName());
        team.setTournament(tournament);
        team.setHeadCoach(player);
        team.setModality(modalityEntity);
        team.setLogoUrl(dto.getLogo());


        if (logo != null && !logo.isEmpty()) {
            team.setLogoUrl("/logos/" + logo.getOriginalFilename());
        }

        return teamMapper.toDto(teamRepository.save(team));
    }

    public List<TeamDTO> getAllTeams(Long modalityId) {
        return teamRepository.findByModality_Id(modalityId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TeamDTO> getAllTeamsWithLogo(Long modality) {
        return teamRepository.findByLogoUrlIsNotNullAndModality_Id_NameIgnoreCase(modality).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TeamDTO> getTeamsByTournament(Long tournamentId, Long modalityId) {
        return teamRepository.findByTournamentIdAndModality_Id(tournamentId, modalityId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TeamDTO> getTeamsByDt(Long dtId) {
        return teamRepository.findByHeadCoachId(dtId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }
}