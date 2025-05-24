package com.hfcommunity.hf_community_hub.assistance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssistanceDTO {
    private String name;
    private LocalDateTime dateTime;
    private String ip;
}
