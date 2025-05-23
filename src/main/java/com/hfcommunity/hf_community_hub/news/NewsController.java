package com.hfcommunity.hf_community_hub.news;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(
            @PathVariable("modality") String modality,
            @RequestBody NewsRequest request
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        request.setModality(modalityId);
        NewsDTO created = newsService.createNews(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNewsByModality(
            @PathVariable("modality") String modality
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<NewsDTO> newsList = newsService.getAllNewsByModality(modalityId);
        return ResponseEntity.ok(newsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNews(
            @PathVariable("modality") String modality,
            @PathVariable Long id,
            @RequestBody NewsRequest request
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        request.setModality(modalityId);
        newsService.updateNews(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
