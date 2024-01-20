package heig.bdr.choochoo.api.geo;

import heig.bdr.choochoo.api.geo.model.HeatmapSegmentFeature;
import heig.bdr.choochoo.api.geo.model.SegmentListFeature;
import heig.bdr.choochoo.api.util.ResultValue;
import heig.bdr.choochoo.business.repository.HeatmapRepository;
import heig.bdr.choochoo.business.repository.PathGeometryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/geography", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GeographyController {

    private final HeatmapRepository heatmapRepository;
    private final PathGeometryRepository pathGeometryRepository;

    @GetMapping(path = "/global/heatmap", produces = "application/geo+json")
    @Transactional(readOnly = true)
    public List<HeatmapSegmentFeature> getGlobalHeatmap(
            @RequestParam(name = "random", required = false, defaultValue = "false") boolean random
    ) {
        return heatmapRepository.getGlobalHeatmap().stream()
                .map(random ? HeatmapSegmentFeature::fromRandom : HeatmapSegmentFeature::from)
                .toList();
    }

    @GetMapping("/global/distance")
    @Transactional(readOnly = true)
    public ResultValue<Long> getTotalCoveredDistance() {
        return ResultValue.of(heatmapRepository.getTotalCoveredDistance());
    }

    @GetMapping(path = "/path/line-string", produces = "application/geo+json")
    @Transactional(readOnly = true)
    public SegmentListFeature getPathLineString(
            @RequestParam(name = "segments") long[] segmentIds
    ) {
        return SegmentListFeature.from(pathGeometryRepository.findPathGeometry(segmentIds));
    }
}
