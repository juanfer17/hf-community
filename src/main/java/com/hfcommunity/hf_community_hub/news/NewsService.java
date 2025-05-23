package com.hfcommunity.hf_community_hub.news;

import com.hfcommunity.hf_community_hub.modality.Modality;
import com.hfcommunity.hf_community_hub.modality.ModalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final ModalityRepository modalityRepository;

    public NewsDTO createNews(NewsRequest request) {
        Modality modality = findModalityById(request.getModality());

        News news = newsMapper.toEntity(request);
        news.setModality(modality);
        news.setPublicationDate(LocalDateTime.now());

        newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    public List<NewsDTO> getAllNewsByModality(Long modalityId) {
        Modality modality = findModalityById(modalityId);

        List<News> newsList = newsRepository.findByModalityOrderByPublicationDateDesc(modality);
        return newsList.stream()
                .map(newsMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateNews(Long id, NewsRequest request) {
        News existing = newsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Noticia no encontrada"));

        Modality modality = findModalityById(request.getModality());

        newsMapper.updateEntityFromRequest(request, existing);
        existing.setModality(modality);

        newsRepository.save(existing);
    }

    public void deleteNews(Long id) {
        News existing = newsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Noticia no encontrada"));

        newsRepository.delete(existing);
    }

    private Modality findModalityById(Long id) {
        return modalityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Modalidad no encontrada"));
    }
}