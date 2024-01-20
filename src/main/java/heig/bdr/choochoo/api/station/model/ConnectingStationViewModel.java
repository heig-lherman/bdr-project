package heig.bdr.choochoo.api.station.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
@Builder
public class ConnectingStationViewModel {

    private final Long opuic;
    private final @NotBlank String name;
    private final @NotBlank String abbreviatedName;
    private final @NotNull Point position;

    private final @NotEmpty long[] edges;
    private final @NotNull int totalDistance;

    private final @NotNull int line;
    private final @NotBlank String lineName;
}
