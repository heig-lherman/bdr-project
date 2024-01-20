package heig.bdr.choochoo.api.geo.model;

import heig.bdr.choochoo.business.repository.data.PathGeometry;
import lombok.Builder;
import lombok.Data;
import org.geolatte.geom.Box;
import org.geolatte.geom.Feature;
import org.geolatte.geom.G2D;
import org.geolatte.geom.MultiLineString;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.jts.JTS;

import java.util.Map;

@Data
@Builder
public class SegmentListFeature implements Feature<G2D, Long> {

    private final Long id;
    private final MultiLineString<G2D> geometry;
    private final Box<G2D> bbox;

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
                "envelope", bbox
        );
    }

    public static SegmentListFeature from(PathGeometry path) {
        var lineString = JTS.from(path.getGeometry(), CoordinateReferenceSystems.WGS84);
        return SegmentListFeature.builder()
                .id(path.getId())
                .geometry(lineString)
                .bbox(lineString.getBoundingBox())
                .build();
    }
}
