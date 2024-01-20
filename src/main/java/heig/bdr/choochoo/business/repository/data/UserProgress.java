package heig.bdr.choochoo.business.repository.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.locationtech.jts.geom.MultiLineString;

@Entity
@Immutable
@Getter
public class UserProgress {

    @Id
    private String userEmail;
    private MultiLineString segments;
    private long travelledCount;
    private long totalCount;
}
