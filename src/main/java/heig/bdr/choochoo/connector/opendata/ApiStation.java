package heig.bdr.choochoo.connector.opendata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import java.util.Arrays;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApiStation {

    @JsonProperty("bpuic")
    @EqualsAndHashCode.Include
    private long opuic;

    @JsonProperty("bezeichnung_offiziell")
    private String name;

    @Nullable
    @JsonProperty("abkuerzung")
    private String abbreviation;

    @JsonProperty("land_iso2_geo")
    private String countryIso2;

    @JsonProperty("kantonskuerzel")
    private String cantonIso2;

    @JsonProperty("kantonsname")
    private String cantonName;

    @JsonProperty("gemeindename")
    private String locality;

    @JsonProperty("bfs_nummer")
    private int localityFsoNumber;

    @JsonProperty("geopos")
    private Geopos position;

    @JsonProperty("bpvh_verkehrsmittel_text_de")
    private String stationType;

    @Nullable
    @JsonIgnore
    public StationType getParsedStationType() {
        return StationType.fromName(stationType);
    }

    public record Geopos(
            @JsonProperty("lon") double longitude,
            @JsonProperty("lat") double latitude
    ) {

        public Point toPoint() {
            var geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
            return geometryFactory.createPoint(new Coordinate(longitude, latitude));
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum StationType {
        BUS_TRAM_TRAIN("BusTramZug", "Bus, Tram, Train"),
        BUS_TRAIN("BusZug", "Bus, Train"),
        CABLECAR_TRAIN("KabinenbahnZug", "Cablecar, Train"),
        FUNICULAR_TRAIN("StandseilbahnZug", "Funicular, Train"),
        TRAM_TRAIN("TramZug", "Tram, Train"),
        TRAIN("Zug", "Train");

        private final String name;
        private final String description;

        public static StationType fromName(String name) {
            return Arrays.stream(StationType.values())
                    .filter(stationType -> stationType.name.equalsIgnoreCase(name))
                    .findAny()
                    .orElse(null);
        }
    }
}
