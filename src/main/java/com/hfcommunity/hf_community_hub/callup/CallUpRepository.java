package com.hfcommunity.hf_community_hub.callup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CallUpRepository extends JpaRepository<CallUp, Long> {

    Optional<CallUp> findByPlayerIdAndModalityId(Long playerId, Long modalityId);

    List<CallUp> findByModalityId(Long modalityId);

    void deleteByPlayerIdAndModalityId(Long playerId, Long modalityId);
}
