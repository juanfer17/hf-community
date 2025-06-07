package com.hfcommunity.hf_community_hub.news;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class NewsRequest {
    private String title;
    private String content;
    private String imageUrl;
    private MultipartFile imagen;
    private String modality;
}
