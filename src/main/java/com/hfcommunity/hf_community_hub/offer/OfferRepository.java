package com.hfcommunity.hf_community_hub.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByPlayerIdAndTeamTournamentModalityId(Long playerId, Long modalityId);

    void deleteByPlayerIdAndTeamTournamentModalityId(Long playerId, Long modalityId);
}
