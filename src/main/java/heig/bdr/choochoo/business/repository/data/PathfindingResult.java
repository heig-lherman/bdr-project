package heig.bdr.choochoo.business.repository.data;

import io.hypersistence.utils.hibernate.type.array.LongArrayType;
import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@Entity
@Immutable
@Getter
public class PathfindingResult {

    @Id
    private Long rank;

    private @Type(LongArrayType.class) long[] stationIds;
    private @Type(StringArrayType.class) String[] stationNames;
    private @Type(StringArrayType.class) String[] stationAbbreviatedNames;

    private @Type(LongArrayType.class) long[] edges;

    private int totalDistance;
}
