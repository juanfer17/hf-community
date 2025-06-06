package com.hfcommunity.hf_community_hub.modality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ModalityRepository extends JpaRepository<Modality, Long> {
}
