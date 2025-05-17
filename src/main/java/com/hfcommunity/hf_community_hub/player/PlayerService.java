package com.hfcommunity.hf_community_hub.player;

import java.util.List;

public interface PlayerService {
    List<PlayerDTO> getAllPlayers();
    PlayerDTO createPlayer(PlayerCreateDTO dto, boolean registered);
    PlayerDTO updateNickhabbo(Long id, String newNick);
    void deletePlayer(Long id);
    List<PlayerDTO> getAllWithRoles();
    PlayerDTO updateRole(Long playerId, String newRole, String requesterRole);
}

