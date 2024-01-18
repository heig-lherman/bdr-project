package heig.bdr.choochoo.business.repository.data;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.locationtech.jts.geom.MultiLineString;

import javax.annotation.concurrent.Immutable;

@Entity
@Immutable
@Getter
public class HeatmapView {

    @Id
    private Long segmentId;
    private MultiLineString segmentGeometry;
    private Long amount;
    private Long total;
}
