package com.hfcommunity.hf_community_hub.callup;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CallUpDTO {
    private Long id;
    private Long playerId;
    private String message;
    private String modality;
    private String nickhabbo;
    private LocalDateTime createdAt;
}
