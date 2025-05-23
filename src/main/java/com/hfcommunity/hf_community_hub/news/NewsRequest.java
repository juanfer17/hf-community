package com.hfcommunity.hf_community_hub.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsRequest {
    private String title;
    private String content;
    private String imageUrl;
    private Long modality;
}
