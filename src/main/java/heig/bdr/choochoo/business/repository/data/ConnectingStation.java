package heig.bdr.choochoo.business.repository.data;

import io.hypersistence.utils.hibernate.type.array.LongArrayType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

@Entity
@Immutable
@Getter
public class ConnectingStation {

    @Id
    private Long opuic;
    private String abbreviatedName;
    private String name;
    private Point geoposition;
    private int line;
    private @Type(LongArrayType.class) long[] edges;
    private int distance;
    private int totalDistance;
    private String lineName;
}
