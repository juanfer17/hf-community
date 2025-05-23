package com.hfcommunity.hf_community_hub.news;

import com.hfcommunity.hf_community_hub.modality.Modality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByPublicationDateDesc();
    List<News> findByModalityOrderByPublicationDateDesc(Modality modality);
}
