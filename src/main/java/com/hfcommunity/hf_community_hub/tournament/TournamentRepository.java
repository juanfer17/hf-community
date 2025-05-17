package com.hfcommunity.hf_community_hub.tournament;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByModality(Modality modality);
    Optional<Tournament> findByIdAndModality_Id(Long id, Long modalityId);
}