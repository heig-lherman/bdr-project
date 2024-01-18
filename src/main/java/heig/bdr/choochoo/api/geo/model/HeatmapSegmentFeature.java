package heig.bdr.choochoo.api.geo.model;

import heig.bdr.choochoo.business.repository.data.HeatmapView;
import lombok.Builder;
import lombok.Data;
import org.geolatte.geom.Box;
import org.geolatte.geom.Feature;
import org.geolatte.geom.G2D;
import org.geolatte.geom.MultiLineString;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.jts.JTS;

import java.util.Map;
import java.util.Random;

@Data
@Builder
public class HeatmapSegmentFeature implements Feature<G2D, Long> {

    private final MultiLineString<G2D> geometry;
    private final Long id;
    private final Double percentage;

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
                "percentage", percentage
        );
    }

    @Override
    public Box<G2D> getBbox() {
        return null;
    }

    public static HeatmapSegmentFeature from(HeatmapView segment) {
        return HeatmapSegmentFeature.builder()
                .id(segment.getSegmentId())
                .geometry(JTS.from(segment.getSegmentGeometry(), CoordinateReferenceSystems.WGS84))
                .percentage(segment.getTotal() > 0 ? segment.getAmount() / (double) segment.getTotal() : 0.0)
                .build();
    }

    public static HeatmapSegmentFeature fromRandom(HeatmapView segment) {
        return HeatmapSegmentFeature.builder()
                .id(segment.getSegmentId())
                .geometry(JTS.from(segment.getSegmentGeometry(), CoordinateReferenceSystems.WGS84))
                .percentage(new Random().nextDouble())
                .build();
    }
}
