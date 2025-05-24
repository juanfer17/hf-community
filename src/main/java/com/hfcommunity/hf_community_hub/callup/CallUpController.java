package com.hfcommunity.hf_community_hub.callup;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/callups")
@RequiredArgsConstructor
public class CallUpController {

    private final CallUpService service;

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("modality") String modality,
            @RequestBody CallUpRequest request) {
        try {
            Long modalityId = ModalityEnum.fromName(modality).getId();
            CallUpDTO dto = service.create(modalityId, request);
            return ResponseEntity.status(201).body(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CallUpDTO>> findByModality(
            @PathVariable("modality") String modality) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<CallUpDTO> callUps = service.findByModality(modalityId);
        return ResponseEntity.ok(callUps);
    }
}
