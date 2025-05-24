package com.hfcommunity.hf_community_hub.assistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
public interface AssistanceRepository extends JpaRepository<Assistance, Long> {
    Optional<Assistance> findFirstByNameAndDateTimeAfter(String name, LocalDateTime since);
}
