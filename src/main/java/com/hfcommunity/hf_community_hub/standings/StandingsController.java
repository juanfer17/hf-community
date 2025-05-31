package com.hfcommunity.hf_community_hub.standings;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{modality}/standings")
@RequiredArgsConstructor
public class StandingsController {

    private final StandingsService standingsService;

    @GetMapping("/tables")
    public List<StandingsDTO> getStandings(
            @PathVariable("modality") String modality,
            @RequestParam("tournamentId") Long tournamentId
    ) {
        Long modalityId = ModalityEnum.fromName(modality).getId();
        return standingsService.getStandings(tournamentId, modalityId);
    }


}
