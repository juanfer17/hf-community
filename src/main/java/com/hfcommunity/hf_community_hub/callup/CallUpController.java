package com.hfcommunity.hf_community_hub.callup;

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
    public ResponseEntity<?> create(@PathVariable("modalityId") Long modalityId, @RequestBody CallUpRequest request) {
        try {
            CallUpDTO dto = service.create(modalityId, request);
            return ResponseEntity.status(201).body(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CallUpDTO>> findByModality(@PathVariable("modalityId") Long modalityId) {
        List<CallUpDTO> callUps = service.findByModality(modalityId);
        return ResponseEntity.ok(callUps);
    }
}
