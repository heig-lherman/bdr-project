package heig.bdr.choochoo.api.geo;

import heig.bdr.choochoo.api.geo.model.HeatmapSegmentFeature;
import heig.bdr.choochoo.business.repository.HeatmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/geography", produces = "application/geo+json")
@RequiredArgsConstructor
public class GeographyController {

    private final HeatmapRepository segmentRepository;

    @GetMapping("/global/heatmap")
    public List<HeatmapSegmentFeature> getGlobalHeatmap(
            @RequestParam(name = "random", required = false, defaultValue = "false") boolean random
    ) {
        return segmentRepository.getGlobalHeatmap().stream()
                .map(random ? HeatmapSegmentFeature::fromRandom : HeatmapSegmentFeature::from)
                .toList();
    }
}
