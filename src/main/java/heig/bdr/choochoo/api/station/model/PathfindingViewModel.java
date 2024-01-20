package heig.bdr.choochoo.api.station.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathfindingViewModel {

    private final @NotEmpty long[] stationIds;
    private final @NotEmpty String[] stationNames;
    private final @NotEmpty String[] stationAbbreviatedNames;

    private final @NotEmpty long[] edges;

    private final @NotNull int totalDistance;
}
