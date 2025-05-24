package com.hfcommunity.hf_community_hub.offer;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<Void> createOffer(
            @PathVariable("modality") String modality,
            @RequestBody OfferRequest request
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        offerService.createOffer(request, modalityId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<List<OfferDTO>> getOffers(
            @PathVariable("modality") String modality,
            @PathVariable Long playerId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        List<OfferDTO> offers = offerService.getOffersByModality(playerId, modalityId);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptOffer(
            @PathVariable("modality") String modality,
            @RequestBody OfferAcceptRequest request
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        offerService.acceptOffer(request, modalityId);
        return ResponseEntity.ok().build();
    }
}
