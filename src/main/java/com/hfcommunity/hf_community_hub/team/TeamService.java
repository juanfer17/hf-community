package com.hfcommunity.hf_community_hub.team;


import com.hfcommunity.hf_community_hub.category.Category;
import com.hfcommunity.hf_community_hub.category.CategoryRepository;
import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import com.hfcommunity.hf_community_hub.player.Player;
import com.hfcommunity.hf_community_hub.player.PlayerRepository;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRole;
import com.hfcommunity.hf_community_hub.playermodalityrol.PlayerModalityRoleRepository;
import com.hfcommunity.hf_community_hub.role.Role;
import com.hfcommunity.hf_community_hub.role.RoleRepository;
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
    private final PlayerModalityRoleRepository playerModalityRoleRepository;
    private final RoleRepository roleRepository;

    public TeamDTO createTeam(TeamCreateDTO dto, MultipartFile logo, Long modality) {

        Modality modalityEntity = modalityRepository.findById(modality)
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no encontrada"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Player player = playerRepository.findById(dto.getDtId())
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

        if (category.getModality() == null ||
                !category.getModality().getName().equalsIgnoreCase(modalityEntity.getName())) {
            throw new IllegalArgumentException("La modalidad proporcionada no coincide con la modalidad de la categoría.");
        }

        // ✅ Verificar si el jugador ya es DT en ese torneo
        boolean alreadyDtInTournament = teamRepository
                .existsByHeadCoachIdAndTournamentId(dto.getDtId(), dto.getTournamentId());

        if (alreadyDtInTournament) {
            throw new IllegalArgumentException("Este jugador ya está registrado como DT en este torneo.");
        }

        Role dtRole = roleRepository.findByNameIgnoreCase("dt")
                .orElseThrow(() -> new IllegalArgumentException("Rol DT no encontrado"));


        // ✅ Crear y guardar el equipo
        Team team = new Team();
        team.setName(dto.getName());
        team.setTournament(tournament);
        team.setHeadCoach(player);
        team.setModality(modalityEntity);
        team.setLogoUrl(dto.getLogo());

        if (logo != null && !logo.isEmpty()) {
            team.setLogoUrl("/logos/" + logo.getOriginalFilename());
        }

        Team savedTeam = teamRepository.save(team);

        // ✅ Registrar rol DT en PlayerModalityRole
        PlayerModalityRole modalityRole = new PlayerModalityRole();
        modalityRole.setPlayer(player);
        modalityRole.setModality(modalityEntity);
        modalityRole.setRole(dtRole);

        playerModalityRoleRepository.save(modalityRole);

        return teamMapper.toDto(savedTeam);
    }


    public List<TeamDTO> getAllTeams(Long modalityId, Long tournamentId) {
        return teamRepository.findByModality_IdAndTournament_Id(modalityId, tournamentId ).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TeamDTO> getAllTeamsWithLogo(Long modality) {
        return teamRepository.findByLogoUrlIsNotNullAndModality_Id(modality).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TeamDTO> getTeamsByTournament(Long tournamentId, Long modalityId) {
        return teamRepository.findByTournamentIdAndModality_Id(tournamentId, modalityId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TeamDTO> getTeamsByDt(Long modalityId, Long dtId) {
        return teamRepository.findByHeadCoachIdAndModalityId(dtId , modalityId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }
}