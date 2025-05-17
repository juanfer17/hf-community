package com.hfcommunity.hf_community_hub.modality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModalityRepository extends JpaRepository<Modality, Long> {
    Optional<Modality> findByNameIgnoreCase(String name);
}
