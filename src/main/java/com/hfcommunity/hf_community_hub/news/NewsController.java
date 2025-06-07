package com.hfcommunity.hf_community_hub.news;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<NewsDTO> createNews(
            @PathVariable("modality") String modality,
            @RequestBody(required = false) NewsRequest jsonRequest,
            @ModelAttribute(binding = false) NewsRequest formRequest
    ) {
        NewsRequest request = jsonRequest != null ? jsonRequest : formRequest;
        if (request.getModality() == null || request.getModality().isEmpty()) {
            request.setModality(modality);
        }
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

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateNews(
            @PathVariable("modality") String modality,
            @PathVariable Long id,
            @RequestBody(required = false) NewsRequest jsonRequest,
            @ModelAttribute(binding = false) NewsRequest formRequest
    ) {
        NewsRequest request = jsonRequest != null ? jsonRequest : formRequest;
        // If modality is not set in the request, use the one from the path
        if (request.getModality() == null || request.getModality().isEmpty()) {
            request.setModality(modality);
        }
        newsService.updateNews(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
