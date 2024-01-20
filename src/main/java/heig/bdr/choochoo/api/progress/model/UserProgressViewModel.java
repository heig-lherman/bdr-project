package heig.bdr.choochoo.api.progress.model;

import heig.bdr.choochoo.business.repository.data.UserProgress;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.geolatte.geom.Box;
import org.geolatte.geom.G2D;
import org.geolatte.geom.MultiLineString;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.jts.JTS;

@Data
@Builder
public class UserProgressViewModel {

    private final @NotNull MultiLineString<G2D> segments;
    private final @NotNull Box<G2D> boundingBox;
    private final @NotNull long travelledCount;
    private final @NotNull long totalCount;

    public static UserProgressViewModel from(UserProgress userProgress) {
        var segments = JTS.from(userProgress.getSegments(), CoordinateReferenceSystems.WGS84);
        return UserProgressViewModel.builder()
                .segments(segments)
                .boundingBox(segments.getBoundingBox())
                .travelledCount(userProgress.getTravelledCount())
                .totalCount(userProgress.getTotalCount())
                .build();
    }
}
