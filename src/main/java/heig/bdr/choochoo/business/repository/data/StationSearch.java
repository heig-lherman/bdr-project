package heig.bdr.choochoo.business.repository.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.locationtech.jts.geom.Point;

@Entity
@Immutable
@Getter
public class StationSearch {

    @Id
    private Long opuic;
    private String name;
    private String abbreviatedName;
    private Point position;
}
