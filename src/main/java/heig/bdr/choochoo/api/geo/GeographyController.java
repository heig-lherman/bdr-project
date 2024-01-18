package heig.bdr.choochoo.api.geo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.LineString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/geography", produces = "application/geo+json")
@RequiredArgsConstructor
public class GeographyController {

    @GetMapping("/global/heatmap")
    public List<LineString> getGlobalHeatmap(
            @RequestParam(name = "random", required = false, defaultValue = "false") boolean random
    ) {
        return null;
    }
}
