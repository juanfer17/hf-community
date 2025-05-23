package com.hfcommunity.hf_community_hub.news;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private String imageUrls;
    private Long modalityId;
    private LocalDateTime publicationDate;
}
