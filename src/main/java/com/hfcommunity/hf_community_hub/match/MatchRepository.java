package com.hfcommunity.hf_community_hub.match;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByModalityOrderByDateDesc(Modality modality);
}